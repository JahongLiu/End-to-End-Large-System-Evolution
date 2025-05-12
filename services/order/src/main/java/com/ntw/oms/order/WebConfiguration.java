package com.ntw.oms.order;

import com.ntw.common.config.AppConfig;
import com.ntw.common.security.AuthenticationInterceptor;
import com.ntw.common.security.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AuthenticationInterceptor())
                .order(1)
                .addPathPatterns(AppConfig.ORDERS_RESOURCE_PATH)
                .addPathPatterns(AppConfig.ORDERS_RESOURCE_PATH +"/**")
                .addPathPatterns(AppConfig.CARTS_RESOURCE_PATH)
                .addPathPatterns(AppConfig.CARTS_RESOURCE_PATH +"/**");

        registry.addInterceptor(new AuthorizationInterceptor())
                .order(2)
                .addPathPatterns(AppConfig.ORDERS_RESOURCE_PATH +"/**")
                .addPathPatterns(AppConfig.CARTS_RESOURCE_PATH +"/**");
    }


}
