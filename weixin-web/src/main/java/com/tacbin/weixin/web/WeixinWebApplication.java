package com.tacbin.weixin.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.tacbin.framework.data.mapper"})
@ComponentScan(basePackages = {"com.tacbin.wexin.service"})
public class WeixinWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinWebApplication.class, args);
    }

}
