package com.yilanjiaju.sulan.module.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }


    @RequestMapping("/apps.html")
    public String apps(){
        return "apps";
    }
}
