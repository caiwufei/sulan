package com.yilanjiaju.log.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO {
    private Integer code;
    private String msg;
    private String requestId;
    private Object data;
}
