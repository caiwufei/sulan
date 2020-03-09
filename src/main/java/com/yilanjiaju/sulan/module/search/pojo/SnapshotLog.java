package com.yilanjiaju.sulan.module.search.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class SnapshotLog {

    private String id;
    private String appId;
    private String command;
    private String mode;
    private String keyword;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
