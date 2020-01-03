package com.yilanjiaju.sulan.module.search.service;

import cn.hutool.core.text.StrSpliter;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
import com.yilanjiaju.sulan.common.AppContext;
import com.yilanjiaju.sulan.module.search.mapper.LogSearchMapper;
import com.yilanjiaju.sulan.module.search.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.pojo.Shell;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class LogSearchService {

    @Autowired
    private LogSearchMapper logSearchMapper;

    public List<HashMap<String, Object>> searchLog(LogSearchParam param){
        List<HashMap<String, Object>> instanceLogList = new ArrayList<>();
        //获取某个应用所有实例；
        List<InstanceInfo> instanceList = logSearchMapper.queryInstanceListByAppId(param.getAppId());

        //String command = "grep -C 5 -r e648a80d99 /appletree/log/qtrade_bond/qtrade_bond_debug.2020-01-01.*.log -m 100";

        String commandTemplate = "grep ${extendCommand} -r ${keyword} ${path} -m ${lines}";
        String finalCommand = StrSubstitutor.replace(commandTemplate,  JSON.parseObject(JSON.toJSONString(param), Map.class));

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
                    instanceMap.put("logList", new ArrayList<>());
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
}
