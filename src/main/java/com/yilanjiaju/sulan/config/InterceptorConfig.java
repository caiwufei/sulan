package com.yilanjiaju.sulan.config;

import com.yilanjiaju.sulan.common.interceptors.AuthenticationInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author caiwufei
 */
@SpringBootConfiguration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(AuthenticationInterceptor())
                                .addPathPatterns("/**")
                                .excludePathPatterns("/api/login.do", "/api/chat/file_download.do", "/api/sync/**");
    }

    @Bean
    public AuthenticationInterceptor AuthenticationInterceptor(){
        return new AuthenticationInterceptor();
    }
}
