package com.yilanjiaju.sulan.module.apps.mapper;

import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.system.pojo.QueryPage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppInfoMapper {

    public int addOneNewApplication(AppInfo appInfo);

    public int editApplication(AppInfo appInfo);

    public int deleteApplication(AppInfo appInfo);

    public List<AppInfo> queryAppList(QueryPage page);
}
