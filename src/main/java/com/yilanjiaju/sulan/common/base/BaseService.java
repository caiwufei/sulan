package com.yilanjiaju.sulan.common.base;

import java.util.Date;

public class BaseService<T extends BaseDTO<T>, D extends BaseMapper<T>> {

    private D d;

    public int insertOne(T dto){
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        return d.insertOne(dto);
    }
}
