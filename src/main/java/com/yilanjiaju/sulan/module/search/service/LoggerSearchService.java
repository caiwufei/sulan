package com.yilanjiaju.sulan.module.search.service;

import com.alibaba.fastjson.JSON;
import com.yilanjiaju.sulan.common.AppContext;
import com.yilanjiaju.sulan.module.apps.mapper.AppInfoMapper;
import com.yilanjiaju.sulan.module.apps.mapper.InstanceInfoMapper;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.pojo.Shell;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class LoggerSearchService {

    @Autowired
    private InstanceInfoMapper instanceInfoMapper;

    @Autowired
    private AppInfoMapper appInfoMapper;

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

        String commandTemplate = "fgrep ${extendCommand} -r ${keyword} ${path} -m ${lines}";
        String finalCommand = StringSubstitutor.replace(commandTemplate,  JSON.parseObject(JSON.toJSONString(param), Map.class));

        CountDownLatch latch = new CountDownLatch(instanceList.size());
        for (InstanceInfo app : instanceList) {
            Shell shell = new Shell();
            shell.setIp(app.getShellHost());
            shell.setPort(app.getShellPort());
            shell.setUsername(app.getShellUser());
            shell.setPassword(app.getShellPass());
            AppContext.getTaskExecutor().execute(()->{
                HashMap<String, Object> instanceMap = new HashMap();
                instanceMap.put("instanceName", app.getAppInstanceName());
                int code = shell.exec(finalCommand);
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
