package com.yilanjiaju.sulan.module.apps.mapper;

import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InstanceInfoMapper {

    public List<InstanceInfo> queryInstanceListByAppId(String appId);

    public List<InstanceInfo> queryInstanceList();

    public int addOneNewInstance(InstanceInfo instanceInfo);

    public int editAppInstance(InstanceInfo instanceInfo);

    public int deleteAppInstance(InstanceInfo instanceInfo);

    public InstanceInfo queryInstanceById(String id);
}
