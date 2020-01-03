package com.yilanjiaju.sulan.module.search.controller;

import com.yilanjiaju.sulan.common.utils.ResponseUtil;
import com.yilanjiaju.sulan.module.search.service.LogSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;

@RestController
public class LogSearchController {

    @Autowired
    private LogSearchService logSearchService;

    @RequestMapping("/search.do")
    public Object searchLog(){
        HashMap<String, List<String>> result = logSearchService.searchLog();
        return ResponseUtil.success(result);
    }
}
