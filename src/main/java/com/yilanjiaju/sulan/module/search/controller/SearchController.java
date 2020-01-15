package com.yilanjiaju.sulan.module.search.controller;

import com.yilanjiaju.sulan.common.utils.ResponseUtil;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/get_log.do")
    public Object searchLog(@RequestBody LogSearchParam param){
        List<HashMap<String, Object>> result = searchService.searchLog(param);
        return ResponseUtil.success(result);
    }

    @RequestMapping("/get_apps.do")
    public Object getApps(){
        List<AppInfo> list = searchService.getApps();
        return ResponseUtil.success("apps", list);
    }
}
