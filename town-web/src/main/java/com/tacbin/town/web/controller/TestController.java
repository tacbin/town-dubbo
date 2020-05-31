package com.tacbin.town.web.controller;

import com.tacbin.town.api.service.TestService;
import com.tacbin.town.api.service.entity.TestEntity;
import com.tacbin.town.web.entity.TestEntityVO;
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
    public TestEntityVO test() {
        TestEntity entity = testService.test();
        TestEntityVO entityVO = new TestEntityVO();
        entityVO.setName(entity.getName());
        entityVO.setComment(entity.getComment());
        return entityVO;
    }
}
