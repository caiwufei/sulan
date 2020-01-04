package com.yilanjiaju.sulan.module.apps.controller;

import com.yilanjiaju.sulan.common.utils.ResponseUtil;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.apps.service.AppManagerService;
import com.yilanjiaju.sulan.module.system.pojo.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;

/**
 * @author caiwufei
 */
@RestController
@RequestMapping("/api")
public class AppManagerController {

    @Autowired
    private AppManagerService appManagerService;

    @RequestMapping("/query_app_list.do")
    public void queryAppList(QueryPage<AppInfo> param){
        appManagerService.queryAppList(param);
    }

    @RequestMapping("/add_app.do")
    public Object addOneNewApplication(@RequestBody AppInfo appInfo){
        int count = appManagerService.addOneNewApplication(appInfo);
        return ResponseUtil.success();
    }

    @RequestMapping("/edit_app.do")
    public Object editApplication(@RequestBody AppInfo appInfo){
        int count = appManagerService.editApplication(appInfo);
        return ResponseUtil.success();
    }

    @RequestMapping("/delete_app.do")
    public Object delelteApplication(@RequestBody AppInfo appInfo){
        int count = appManagerService.deleteApplication(appInfo);
        return ResponseUtil.success();
    }

    @RequestMapping("/add_app_instance.do")
    public Object addOneNewInstance(@RequestBody InstanceInfo instanceInfo){
        int count = appManagerService.addOneNewInstance(instanceInfo);
        return ResponseUtil.success();
    }

    @RequestMapping("/edit_app_instance.do")
    public Object editAppInstance(@RequestBody InstanceInfo instanceInfo){
        int count = appManagerService.editAppInstance(instanceInfo);
        return ResponseUtil.success();
    }

    @RequestMapping("/delete_app_instance.do")
    public Object deleteAppInstance(@RequestBody InstanceInfo instanceInfo){
        int count = appManagerService.deleteAppInstance(instanceInfo);
        return ResponseUtil.success();
    }

}
