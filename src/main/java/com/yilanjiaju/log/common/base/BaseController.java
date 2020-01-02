package com.yilanjiaju.log.common.base;

import com.yilanjiaju.log.common.utils.ResponseUtil;

public class BaseController {

    public ResponseVO success(Object o){
        return ResponseUtil.success(o);
    }

    public ResponseVO success(){
        return ResponseUtil.success();
    }

    public ResponseVO errorCheck(){
        return ResponseUtil.errorCheck();
    }

    public ResponseVO errorRun(){
        return ResponseUtil.errorRun();
    }

    public ResponseVO success(String key, Object value){
        return ResponseUtil.success(key, value);
    }
}
