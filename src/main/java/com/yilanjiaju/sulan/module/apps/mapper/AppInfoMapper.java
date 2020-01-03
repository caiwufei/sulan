package com.yilanjiaju.sulan.module.apps.mapper;

import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoMapper {

    public int addOneNewApplication(AppInfo appInfo);

    public int editApplication(AppInfo appInfo);

    public int deleteApplication(AppInfo appInfo);

}
