package com.tacbin.town.service.service;

import com.tacbin.town.api.service.TestService;
import com.tacbin.town.api.service.entity.TestEntity;
import com.tacbin.town.repo.entity.Test;
import com.tacbin.town.repo.service.ITestService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author tacbin
 * @createTime 2020/5/9 10:03
 * @description
 **/
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private ITestService testService;

    @Override
    public TestEntity test() {
        Test test = testService.getById(1l);
        TestEntity testEntity = new TestEntity();
        testEntity.setName(test.getName());
        testEntity.setComment(test.getComment());
        return testEntity;
    }
}
