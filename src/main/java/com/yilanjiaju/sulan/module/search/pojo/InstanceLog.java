package com.yilanjiaju.sulan.module.search.pojo;

import lombok.Data;
import java.util.List;

@Data
public class InstanceLog {
    private String instanceName;
    private List<String> logList;
}
