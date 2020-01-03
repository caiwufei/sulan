package com.yilanjiaju.sulan.module.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.yilanjiaju.sulan.common.utils.ResponseUtil;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.service.LogSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;

@RestController
public class LogSearchController {

    @Autowired
    private LogSearchService logSearchService;

    @RequestMapping("/search.do")
    public Object searchLog(@RequestBody LogSearchParam param){
        List<HashMap<String, Object>> result = logSearchService.searchLog(param);
        return ResponseUtil.success(result);
    }
}
