package com.yilanjiaju.sulan.module.apps.controller;

import com.yilanjiaju.sulan.common.utils.ResponseUtil;
import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.apps.service.AppManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author caiwufei
 */
@RestController
@RequestMapping("/api/apps")
public class AppManagerController {

    @Autowired
    private AppManagerService appManagerService;

    @RequestMapping("/query_app_list.do")
    public Object queryAppList(){
        List<AppInfo> list = appManagerService.queryAllAppList();
        return ResponseUtil.success("apps", list);
    }

    @RequestMapping("/get_instance_list.do")
    public Object queryInstanceList(@RequestBody AppInfo appInfo){
        List<InstanceInfo> list = appManagerService.queryInstanceList(appInfo.getAppId());
        return ResponseUtil.success("instances", list);
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

    @RequestMapping("/add_instance.do")
    public Object addOneNewInstance(@RequestBody InstanceInfo instanceInfo){
        int count = appManagerService.addOneNewInstance(instanceInfo);
        return ResponseUtil.success();
    }

    @RequestMapping("/edit_instance.do")
    public Object editAppInstance(@RequestBody InstanceInfo instanceInfo){
        int count = appManagerService.editAppInstance(instanceInfo);
        return ResponseUtil.success();
    }

    @RequestMapping("/delete_instance.do")
    public Object deleteAppInstance(@RequestBody InstanceInfo instanceInfo){
        int count = appManagerService.deleteAppInstance(instanceInfo);
        return ResponseUtil.success();
    }

}
