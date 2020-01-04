package com.yilanjiaju.sulan.module.system.pojo;

import java.util.List;

public class QueryPage<E> {
    private Integer page;
    private Integer size;
    private Integer offset;
    private Integer total;
    private List<E> list;
}
