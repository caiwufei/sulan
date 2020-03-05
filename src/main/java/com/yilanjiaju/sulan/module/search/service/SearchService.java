package com.yilanjiaju.sulan.module.search.service;

import com.alibaba.fastjson.JSON;
import com.yilanjiaju.sulan.common.AppContext;
import com.yilanjiaju.sulan.common.utils.AESUtil;
import com.yilanjiaju.sulan.module.apps.mapper.AppInfoMapper;
import com.yilanjiaju.sulan.module.apps.mapper.InstanceInfoMapper;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.pojo.Shell;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private InstanceInfoMapper instanceInfoMapper;

    @Autowired
    private AppInfoMapper appInfoMapper;

    private ConcurrentHashMap<String, String> userPass = new ConcurrentHashMap<>();

    private final static String grepCommandTemplate = "fgrep ${extendCommand} -r ${keyword} ${path} -m ${lines}";

    private final static String tailCommandTemplate = "tail -n ${lines} ${path}";

    @PostConstruct
    public void initData(){
        loadHostPassCache();
    }

    @Scheduled(cron = "0 */5 * * * ?")
    private void loadHostPassCache(){
        List<InstanceInfo> instanceList = instanceInfoMapper.queryInstanceList();
        for (InstanceInfo app : instanceList) {
            String key = app.getShellHost() + "_" + app.getShellPort() + "_" + app.getShellUser();
            if (userPass.get(key) == null) {
                try {
                    String password = AESUtil.decrypt(app.getShellPass(), AESUtil.KEY);
                    userPass.put(key, password);
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 日志搜索
     * grep -C 5 -r e648a80d99 /appletree/log/qtrade_bond/qtrade_bond_debug.2020-01-01.*.log -m 100
     * @param param
     * @return
     */
    public List<HashMap<String, Object>> searchLog(LogSearchParam param){
        List<HashMap<String, Object>> instanceLogList = new ArrayList<>();
        //获取某个应用所有实例；
        List<InstanceInfo> instanceList = instanceInfoMapper.queryInstanceListByAppId(param.getAppId());

        String finalCommand = null;
        if (StringUtils.isBlank(param.getKeyword()) || StringUtils.equals("*", param.getKeyword())) {
            finalCommand = StringSubstitutor.replace(tailCommandTemplate,  JSON.parseObject(JSON.toJSONString(param), Map.class));
        } else {
            finalCommand = StringSubstitutor.replace(grepCommandTemplate,  JSON.parseObject(JSON.toJSONString(param), Map.class));
        }

        CountDownLatch latch = new CountDownLatch(instanceList.size());
        for (InstanceInfo app : instanceList) {
            Shell shell = new Shell();
            shell.setIp(app.getShellHost());
            shell.setPort(app.getShellPort());
            shell.setUsername(app.getShellUser());
            String key = app.getShellHost() + "_" + app.getShellPort() + "_" + app.getShellUser();
            if (userPass.get(key) == null) {
                try {
                    shell.setPassword(AESUtil.decrypt(app.getShellPass(), AESUtil.KEY));
                    userPass.put(key, shell.getPassword());
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            } else {
                shell.setPassword(userPass.get(key));
            }

            String finalCommand1 = finalCommand;
            AppContext.getTaskExecutor().execute(()->{
                HashMap<String, Object> instanceMap = new HashMap();
                instanceMap.put("instanceName", app.getAppInstanceName());
                int code = shell.exec(finalCommand1);
                List<String> logList = shell.getStdout();
                if(CollectionUtils.isEmpty(logList)){
                    instanceMap.put("logList", Arrays.asList("--- no data ---"));
                } else {
                    String highLight = "<a class='highLight'>" + param.getKeyword() + "</a>";
                    logList = logList.stream().map(e-> e.replaceAll(param.getKeyword(), highLight)).collect(Collectors.toList());
                    instanceMap.put("logList", logList);
                }
                instanceLogList.add(instanceMap);
                latch.countDown();
            });

        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instanceLogList;
    }

    public List<AppInfo> getApps(){
        List<AppInfo> list = appInfoMapper.queryAllAppList();
        if(null==list) {
            list = new ArrayList<>();
        }
        return list;
    }
}
