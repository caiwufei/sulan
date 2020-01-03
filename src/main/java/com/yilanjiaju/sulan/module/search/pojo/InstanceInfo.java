package com.yilanjiaju.sulan.module.search.pojo;

import lombok.Data;

@Data
public class InstanceInfo {

    private String id;
    private String appId;
    private String appInstanceName;
    private String shellUser;
    private String shellPass;
    private String shellHost;
    private Integer shellPort;
    private Integer status;
}
