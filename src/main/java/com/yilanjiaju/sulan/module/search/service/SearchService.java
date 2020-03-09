package com.yilanjiaju.sulan.module.search.service;

import com.alibaba.fastjson.JSON;
import com.yilanjiaju.sulan.common.AppContext;
import com.yilanjiaju.sulan.common.constant.CommandTemplate;
import com.yilanjiaju.sulan.common.utils.AESUtil;
import com.yilanjiaju.sulan.module.apps.mapper.AppInfoMapper;
import com.yilanjiaju.sulan.module.apps.mapper.InstanceInfoMapper;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.pojo.Shell;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SearchService {

    @Autowired
    private InstanceInfoMapper instanceInfoMapper;

    @Autowired
    private AppInfoMapper appInfoMapper;

    private ConcurrentHashMap<String, String> userPass = new ConcurrentHashMap<>();

    @PostConstruct
    public void initData(){
        loadHostPassCache();
    }

    @Scheduled(cron = "0 */5 * * * ?")
    private void loadHostPassCache(){
        List<InstanceInfo> instanceList = instanceInfoMapper.queryInstanceList();
        for (InstanceInfo app : instanceList) {
            String key = app.getShellHost() + "_" + app.getShellPort() + "_" + app.getShellUser();
            log.info("------------key=={}", key);
            if (userPass.get(key) == null) {
                try {
                    String password = AESUtil.decrypt(app.getShellPass(), AESUtil.KEY);
                    userPass.put(key, password);
                } catch (Exception e) {
                    log.info("-----------loadHostPassCache exception=={}", e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 日志搜索
     * @param param
     * @return
     */
    public List<HashMap<String, Object>> searchLog(LogSearchParam param){
        List<HashMap<String, Object>> instanceLogList = new ArrayList<>();
        //获取某个应用所有实例；
        List<InstanceInfo> instanceList = instanceInfoMapper.queryInstanceListByAppId(param.getAppId());

        String finalCommand = StringSubstitutor.replace(CommandTemplate.getTemplateByName(param.getMode()),
                                                        JSON.parseObject(JSON.toJSONString(param), Map.class));

        log.info("--------search command=={}", finalCommand);
        CountDownLatch latch = new CountDownLatch(instanceList.size());
        for (InstanceInfo app : instanceList) {
            //加载shell，并登陆
            Shell shell = getShellFromAppInfoConfig(app);
            //使用shell执行搜索脚本操作
            AppContext.getTaskExecutor().execute(()->{
                HashMap<String, Object> instanceMap = new HashMap();
                instanceMap.put("instanceName", app.getAppInstanceName());
                shell.exec(finalCommand);
                List<String> logList = shell.getStdout();
                if(CollectionUtils.isEmpty(logList)){
                    //没有结果，那么返回no-data
                    instanceMap.put("logList", Arrays.asList("--- no data ---"));
                } else {
                    //如果使用tac+grep，把结果翻转
                    if (param.getMode().equals(CommandTemplate.tac.getName())) {
                        Collections.reverse(logList);
                    }
                    //针对grep和tac+grep的做关键词高亮
                    if(param.getMode().equals(CommandTemplate.grep.getName()) || param.getMode().equals(CommandTemplate.tac.getName())) {
                        String highLight = "<a class='highLight'>" + param.getKeyword() + "</a>";
                        logList = logList.stream().map(e-> e.replaceAll(param.getKeyword(), highLight)).collect(Collectors.toList());
                    }
                    instanceMap.put("logList", logList);
                }
                instanceLogList.add(instanceMap);
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.info("---------------------latch.await() exception == {}", e);
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

    private Shell getShellFromAppInfoConfig(InstanceInfo app){
        Shell shell = new Shell();
        shell.setIp(app.getShellHost());
        shell.setPort(app.getShellPort());
        shell.setUsername(app.getShellUser());
        String key = app.getShellHost() + "_" + app.getShellPort() + "_" + app.getShellUser();
        if (userPass.get(key) == null) {
            try {
                shell.setPassword(AESUtil.decrypt(app.getShellPass(), AESUtil.KEY));
                userPass.put(key, shell.getPassword());
            } catch (Exception e) {
                log.info("---------------------get userPass exception == {}", e);
                e.printStackTrace();
            }
        } else {
            shell.setPassword(userPass.get(key));
        }
        return shell;
    }
}
