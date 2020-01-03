package com.yilanjiaju.sulan.module.apps.service;

import com.yilanjiaju.sulan.common.utils.CommonUtil;
import com.yilanjiaju.sulan.module.apps.mapper.AppInfoMapper;
import com.yilanjiaju.sulan.module.apps.mapper.InstanceInfoMapper;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppManagerService {

    @Autowired
    private AppInfoMapper appInfoMapper;
    @Autowired
    private InstanceInfoMapper instanceInfoMapper;

    public int addOneNewApplication(AppInfo appInfo){
        appInfo.setId(CommonUtil.uuid());
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
}
