package com.adinstar.pangyo.config;

import com.adinstar.pangyo.controller.interceptor.LoginInterceptor;
import com.adinstar.pangyo.controller.interceptor.PathVariableInterceptor;
import com.adinstar.pangyo.filter.SiteMeshFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
                .addPathPatterns("/campaign/**")
                .addPathPatterns("/fanClub/**")
                .addPathPatterns("/star/**")
                .addPathPatterns("/member/**");

        registry.addInterceptor(pathVariableInterceptor)
                .addPathPatterns("/api/**")
                .addPathPatterns("/fanClub/**")
                .addPathPatterns("/campaign/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/images/");
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/decorator/default").setViewName("decorator/default");
        registry.addViewController("/decorator/gnb").setViewName("decorator/gnb");
        registry.addViewController("/decorator/backMenu").setViewName("decorator/backMenu");
    }

    @Bean
    public FilterRegistrationBean siteMeshFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        SiteMeshFilter siteMeshFilter = new SiteMeshFilter();
        filter.setFilter(siteMeshFilter);
        return filter;
    }
}
