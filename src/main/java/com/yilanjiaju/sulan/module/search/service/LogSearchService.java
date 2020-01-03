package com.yilanjiaju.sulan.module.search.service;

import com.yilanjiaju.sulan.common.AppContext;
import com.yilanjiaju.sulan.module.search.mapper.LogSearchMapper;
import com.yilanjiaju.sulan.module.search.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.search.pojo.Shell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class LogSearchService {

    @Autowired
    private LogSearchMapper logSearchMapper;

    public HashMap<String, List<String>> searchLog(){
        HashMap<String, List<String>> resultMap = new HashMap<>();
        //获取某个应用所有实例；
        String appId = "8e3bf145813448cb9bed0c7b4422a4e4";
        List<InstanceInfo> instanceList = logSearchMapper.queryInstanceListByAppId(appId);

        String command = "grep -C 5 -r e648a80d99 /appletree/log/qtrade_bond/qtrade_bond_debug.2020-01-01.*.log";

        CountDownLatch latch = new CountDownLatch(instanceList.size());
        for (InstanceInfo app : instanceList) {
            Shell shell = new Shell();
            shell.setIp(app.getShellHost());
            shell.setPort(app.getShellPort());
            shell.setUsername(app.getShellUser());
            shell.setPassword(app.getShellPass());
            AppContext.getTaskExecutor().execute(()->{
                int code = shell.exec(command);
                resultMap.put(app.getAppInstanceName(), shell.getStdout());
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
