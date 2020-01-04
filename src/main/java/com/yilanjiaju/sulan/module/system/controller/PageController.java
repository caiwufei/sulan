package com.yilanjiaju.sulan.module.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }

    @RequestMapping("/search.html")
    public String search(){
        return "search";
    }

    @RequestMapping("/apps.html")
    public String apps(){
        return "apps";
    }


    @RequestMapping("/tab1.html")
    public String tab1(){
        return "tab1";
    }

    @RequestMapping("/tab2.html")
    public String tab2(){
        return "tab2";
    }
}
