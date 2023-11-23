package com.leon.config;

import com.leon.controller.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyConfig implements WebMvcConfigurer {

    //访问下列连接将会转发到一下静态资源中
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/jumpLogin").setViewName("login");
        registry.addViewController("/jumpRegister").setViewName("register");
    }
    //不暴露图片资源真实路径的路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:C:\\Users\\12963\\Desktop\\学习\\毕设\\代码测试\\secondHandTransaction\\src\\main\\resources\\static\\images\\");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/","/index.html","/login","/register","/jumpLogin","/jumpRegister","/css/*","/js/*","/img/*");
    }
}
