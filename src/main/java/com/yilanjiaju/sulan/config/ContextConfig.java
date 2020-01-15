package com.yilanjiaju.sulan.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootConfiguration
@ComponentScan(value= "com.yilanjiaju.sulan")
@EnableScheduling
public class ContextConfig {

}
