package com.tacbin.weixin.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan({"com.tacbin.framework.data.mapper"})
@ComponentScan(basePackages = {"com.tacbin.weixin.web","com.tacbin.wexin.service","com.tacbin.weixin.common"})
@EnableSwagger2
public class WeixinWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinWebApplication.class, args);
    }

}
