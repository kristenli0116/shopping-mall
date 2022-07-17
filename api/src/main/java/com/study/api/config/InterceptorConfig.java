package com.study.api.config;

import com.study.api.interceptor.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther lkx
 * @create 2022-05-01 22:34
 * @Description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/shopCart/**")
                .addPathPatterns("/orders/**")
                .addPathPatterns("/shopCart/**")
                .excludePathPatterns("/user/**");
    }
}
