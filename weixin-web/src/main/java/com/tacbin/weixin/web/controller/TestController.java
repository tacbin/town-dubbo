package com.tacbin.weixin.web.controller;

import com.tacbin.weixin.service.TestService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tacbin
 * @createTime 2020/5/9 10:10
 * @description
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    @Reference
    private TestService testService;

    @RequestMapping("/success")
    public String test() {
        return testService.test();
    }
}
