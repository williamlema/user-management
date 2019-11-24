package com.user.management.app.config;

import com.user.management.app.config.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> loggingFilter(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/user/*","/rol/*");

        return registrationBean;
    }
}
