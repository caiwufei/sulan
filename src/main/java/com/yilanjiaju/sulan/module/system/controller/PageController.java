package com.yilanjiaju.sulan.module.system.controller;

import com.yilanjiaju.sulan.module.apps.pojo.AppInfo;
import com.yilanjiaju.sulan.module.apps.pojo.InstanceInfo;
import com.yilanjiaju.sulan.module.apps.service.AppManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    @Autowired
    private AppManagerService appManagerService;

    @RequestMapping("/index.html")
    public String index(){
        return "search";
    }

    @RequestMapping("/search.html")
    public String search(){
        return "search";
    }

    @RequestMapping("/apps.html")
    public String apps(){
        return "apps";
    }

    @RequestMapping("/app-manage/add_app.html")
    public String addApp(){
        return "app-manage/add_app";
    }

    @RequestMapping("/app-manage/edit_app.html")
    public ModelAndView editApp(ModelAndView mv, @RequestParam("appId") String appId){
        mv.setViewName("app-manage/edit_app");
        mv.getModel().put("app", appManagerService.queryAppByAppId(appId));
        return mv;
    }

    @RequestMapping("/app-manage/add_instance.html")
    public ModelAndView addInstance(ModelAndView mv, @RequestParam("appId") String appId){
        mv.setViewName("app-manage/add_instance");
        mv.getModel().put("appId", appId);
        return mv;
    }

    @RequestMapping("/app-manage/edit_instance.html")
    public ModelAndView editInstance(ModelAndView mv, @RequestParam("id") String id){
        mv.setViewName("app-manage/edit_instance");
        mv.getModel().put("instance", appManagerService.queryInstanceById(id));
        return mv;
    }

    @RequestMapping("/snapshot.html")
    public ModelAndView snapshot(ModelAndView mv, @RequestParam("id") String id){
        mv.setViewName("snapshot");
        mv.getModel().put("snapshotId", id);
        return mv;
    }

}
