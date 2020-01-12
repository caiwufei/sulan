package com.yilanjiaju.sulan.module.apps.service;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import com.yilanjiaju.sulan.common.utils.CommonUtil;
import com.yilanjiaju.sulan.module.apps.mapper.AppInfoMapper;
import com.yilanjiaju.sulan.module.apps.mapper.InstanceInfoMapper;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.system.pojo.QueryPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class AppManagerService {

    @Autowired
    private AppInfoMapper appInfoMapper;
    @Autowired
    private InstanceInfoMapper instanceInfoMapper;

    public void queryAppList(QueryPage<AppInfo> param){
        appInfoMapper.queryAppList(param);
    }

    public int addOneNewApplication(AppInfo appInfo){
        appInfo.setId(CommonUtil.uuid());
        appInfo.setAppId(appInfo.getId());
        return appInfoMapper.addOneNewApplication(appInfo);
    }

    public int editApplication(AppInfo appInfo){
        return appInfoMapper.editApplication(appInfo);
    }

    public int deleteApplication(AppInfo appInfo){
        return appInfoMapper.deleteApplication(appInfo);
    }

    public int addOneNewInstance(InstanceInfo instanceInfo){
        instanceInfo.setId(CommonUtil.uuid());
        return instanceInfoMapper.addOneNewInstance(instanceInfo);
    }

    public int editAppInstance(InstanceInfo instanceInfo){
        return instanceInfoMapper.editAppInstance(instanceInfo);
    }

    public int deleteAppInstance(InstanceInfo instanceInfo){
        return instanceInfoMapper.deleteAppInstance(instanceInfo);
    }

    public List<AppInfo> queryAllAppList(){
        return appInfoMapper.queryAllAppList();
    }

    public List<InstanceInfo> queryInstanceList(String appId){
        List<InstanceInfo> list = instanceInfoMapper.queryInstanceListByAppId(appId);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(i->i.setShellPass("******"));
        }
        return list;
    }

    public AppInfo queryAppByAppId(String appId) {
        return appInfoMapper.queryAppByAppId(appId);
    }

    public InstanceInfo queryInstanceById(String id) {
        return instanceInfoMapper.queryInstanceById(id);
    }
}
