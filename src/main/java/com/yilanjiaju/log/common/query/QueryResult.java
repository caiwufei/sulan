package com.yilanjiaju.log.common.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResult<E> {
    private List<E> list;
    private Integer page;
    private Integer size;
    private Long total;

    public QueryResult(){}

    public QueryResult(QueryParam param){
        if(null!=param.getSize()){
            this.size = param.getSize();
        }
        if(null != param.getPage()){
            this.page = param.getPage();
        }
    }
}
