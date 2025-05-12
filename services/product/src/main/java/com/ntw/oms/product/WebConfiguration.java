package com.ntw.oms.product;

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
                .addPathPatterns(AppConfig.PRODUCTS_RESOURCE_PATH)
                .addPathPatterns(AppConfig.PRODUCTS_RESOURCE_PATH +"/**");

        registry.addInterceptor(new AuthorizationInterceptor())
                .order(2)
                .addPathPatterns(AppConfig.PRODUCTS_RESOURCE_PATH +"/**");
    }
}
