package com.yilanjiaju.sulan.module.search.pojo;

import lombok.Data;

@Data
public class LogSearchParam {

    private String keyword;

    private String path;

    private String extendCommand = "";

    private String appId;

    private Integer lines = 500;
}
