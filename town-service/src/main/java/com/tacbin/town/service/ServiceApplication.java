package com.tacbin.town.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author tacbin
 * @createTime 2020/5/8 20:41
 * @description
 **/
@SpringBootApplication(scanBasePackages = {"com.tacbin.town"})
@MapperScan(basePackages = "com.tacbin.town.repo.mapper")
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
