package com.yilanjiaju.sulan.module.search.service;

import com.alibaba.fastjson.JSON;
import com.yilanjiaju.sulan.common.AppContext;
import com.yilanjiaju.sulan.common.constant.CommandTemplate;
import com.yilanjiaju.sulan.common.utils.AESUtil;
import com.yilanjiaju.sulan.module.apps.mapper.AppInfoMapper;
import com.yilanjiaju.sulan.module.apps.mapper.InstanceInfoMapper;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.search.pojo.InstanceLog;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.pojo.Shell;
import com.yilanjiaju.sulan.module.search.pojo.SnapshotLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchService {

    @Autowired
    private InstanceInfoMapper instanceInfoMapper;

    @Autowired
    private AppInfoMapper appInfoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
    public List<InstanceLog> searchLog(LogSearchParam param){
        List<InstanceLog> instanceLogList = new ArrayList<>();
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
                InstanceLog instanceLog = new InstanceLog();
                instanceLog.setInstanceName(app.getAppInstanceName());
                shell.exec(finalCommand);
                List<String> logList = shell.getStdout();
                if(CollectionUtils.isEmpty(logList)){
                    //没有结果，那么返回no-data
                    instanceLog.setLogList(Arrays.asList("--- no data ---"));
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
                    instanceLog.setLogList(logList);
                }
                instanceLogList.add(instanceLog);
                latch.countDown();
            });
        }

        try {
            latch.await();

            SnapshotLog snapshotLog = new SnapshotLog();
            snapshotLog.setId(AppContext.getRequestLogId());
            snapshotLog.setCommand(finalCommand);
            snapshotLog.setMode(param.getMode());
            snapshotLog.setAppId(param.getAppId());
            if(StringUtils.isNotBlank(param.getKeyword())) {
                snapshotLog.setKeyword(param.getKeyword());
            }
            redisTemplate.opsForValue().set("sulan::log_snapshot_info::"+snapshotLog.getId(), snapshotLog.toString(), 1,  TimeUnit.DAYS);
        } catch (InterruptedException e) {
            log.info("---------------------latch.await() exception == {}", e);
            e.printStackTrace();
        }
        return instanceLogList;
    }


    public List<InstanceLog> searchSnapshotLog(SnapshotLog param){
        List<InstanceLog> instanceLogList = new ArrayList<>();

        String cachestring = (String) redisTemplate.opsForValue().get("sulan::log_snapshot_info::" + param.getId());
        if (StringUtils.isBlank(cachestring)) {
            InstanceLog instanceLog = new InstanceLog();
            instanceLog.setInstanceName("null");
            instanceLog.setLogList(Arrays.asList("--- no data, log snapshot may has expired. ---"));
            instanceLogList.add(instanceLog);
            return instanceLogList;
        }

        //获取某个应用所有实例；
        SnapshotLog snapshotLog = JSON.parseObject(cachestring, SnapshotLog.class);
        List<InstanceInfo> instanceList = instanceInfoMapper.queryInstanceListByAppId(snapshotLog.getAppId());
        CountDownLatch latch = new CountDownLatch(instanceList.size());
        for (InstanceInfo app : instanceList) {
            //加载shell，并登陆
            Shell shell = getShellFromAppInfoConfig(app);
            //使用shell执行搜索脚本操作
            AppContext.getTaskExecutor().execute(()->{
                InstanceLog instanceLog = new InstanceLog();
                instanceLog.setInstanceName(app.getAppInstanceName());
                shell.exec(snapshotLog.getCommand());
                List<String> logList = shell.getStdout();
                if(CollectionUtils.isEmpty(logList)){
                    //没有结果，那么返回no-data
                    instanceLog.setLogList(Arrays.asList("--- no data ---"));
                } else {
                    //如果使用tac+grep，把结果翻转
                    if (snapshotLog.getMode().equals(CommandTemplate.tac.getName())) {
                        Collections.reverse(logList);
                    }
                    //针对grep和tac+grep的做关键词高亮
                    if(snapshotLog.getMode().equals(CommandTemplate.grep.getName()) || snapshotLog.getMode().equals(CommandTemplate.tac.getName())) {
                        String highLight = "<a class='highLight'>" + snapshotLog.getKeyword() + "</a>";
                        logList = logList.stream().map(e-> e.replaceAll(snapshotLog.getKeyword(), highLight)).collect(Collectors.toList());
                    }
                    instanceLog.setLogList(logList);
                }
                instanceLogList.add(instanceLog);
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
