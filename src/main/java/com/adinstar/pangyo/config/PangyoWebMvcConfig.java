package com.adinstar.pangyo.config;

import com.adinstar.pangyo.controller.interceptor.LoginInterceptor;
import com.adinstar.pangyo.controller.interceptor.PathVariableInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class PangyoWebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private PathVariableInterceptor pathVariableInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .addPathPatterns("/fanClub/**")
                .addPathPatterns("/star/**")
                .addPathPatterns("/member/**");

        registry.addInterceptor(pathVariableInterceptor)
                .addPathPatterns("/api/**")
                .addPathPatterns("/fanClub/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/images/");
    }
}
