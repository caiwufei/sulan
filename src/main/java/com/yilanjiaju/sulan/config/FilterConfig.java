package com.yilanjiaju.sulan.config;

import com.yilanjiaju.sulan.common.filters.LogFilter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LogFilter> registLogFilter() {
        FilterRegistrationBean logFilter = new FilterRegistrationBean();
        logFilter.setFilter(new LogFilter());
        logFilter.addUrlPatterns("/*");
        logFilter.setName("LogFilter");
        logFilter.setOrder(1);
        return logFilter;
    }
}
