package com.tacbin.town.web.config;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description :图片映射
 * @Author : Administrator
 * @Date : 2020-03-12 10:55
 **/
@Component
public class WebFileConfig implements WebMvcConfigurer {
    // 在D:/res/pic下如果有一张 luffy.jpg.jpg的图片，那么：
    // 1 访问：http://localhost:8080/img/luffy.jpg 可以访问到
    // 2 html 中 <img src="http://localhost:8080/img/luffy.jpg">
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String userHome = System.getProperty("user.home");
        String imgPath = "file:" + userHome + "/app/images/";
        //imgPath-> file:/root/app/images/
        registry.addResourceHandler("/images/**").addResourceLocations(imgPath);
        // file
        String filePath = "file:" + userHome + "/app/files/";
        registry.addResourceHandler("/files/**").addResourceLocations(filePath);
    }
}