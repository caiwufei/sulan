package com.yilanjiaju.sulan.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO<T> implements Serializable {

    private String id;
    private int dataStatus;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
}
