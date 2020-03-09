package com.yilanjiaju.sulan.module.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.yilanjiaju.sulan.common.AppContext;
import com.yilanjiaju.sulan.common.utils.ResponseUtil;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.search.pojo.InstanceLog;
import com.yilanjiaju.sulan.module.search.pojo.LogSearchParam;
import com.yilanjiaju.sulan.module.search.pojo.SnapshotLog;
import com.yilanjiaju.sulan.module.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/get_log.do")
    public Object searchLog(@RequestBody LogSearchParam param){
        JSONObject json = new JSONObject();

        List<InstanceLog> logList = searchService.searchLog(param);

        json.put("snapshotId", AppContext.getRequestLogId());
        json.put("instanceLogList", logList);

        return ResponseUtil.success(json);
    }

    @RequestMapping("/get_log_snapshot.do")
    public Object searchSnapshotLog(@RequestBody SnapshotLog param){
        JSONObject json = new JSONObject();

        List<InstanceLog> logList = searchService.searchSnapshotLog(param);

        json.put("instanceLogList", logList);

        return ResponseUtil.success(json);
    }

    @RequestMapping("/get_apps.do")
    public Object getApps(){
        List<AppInfo> list = searchService.getApps();
        return ResponseUtil.success("apps", list);
    }
}
