package com.yilanjiaju.sulan.module.apps.pojo;

import lombok.Data;

import java.util.List;

@Data
public class AppInfo {

    private String id;

    private String appId;

    private String appName;

    private String logPath;

    private List<InstanceInfo> instanceInfoList;
}
