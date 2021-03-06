package com.yilanjiaju.sulan.common.query;

import com.yilanjiaju.sulan.common.base.BaseDTO;
import lombok.Data;

@Data
public class QueryParam extends BaseDTO<QueryParam> {
    private String keyword;
    private Integer size;
    private Integer page;
    private Integer offset;
    private Integer type;
}
