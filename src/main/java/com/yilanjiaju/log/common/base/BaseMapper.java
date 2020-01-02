package com.yilanjiaju.log.common.base;

public interface BaseMapper<T extends BaseDTO> {

    public int insertOne(T t);

    public int updateOne(T t);

    public int deleteOne(T t);

    public T selectOne(T t) ;
}
