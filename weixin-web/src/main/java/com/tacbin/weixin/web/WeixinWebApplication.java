package com.tacbin.weixin.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.tacbin.framework.data.mapper"})
public class WeixinWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinWebApplication.class, args);
    }

}
