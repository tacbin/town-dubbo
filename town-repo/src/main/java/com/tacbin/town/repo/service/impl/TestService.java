package com.tacbin.town.repo.service.impl;

import com.tacbin.town.repo.entity.Test;
import com.tacbin.town.repo.mapper.ITestMapper;
import com.tacbin.town.repo.service.ITestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 19:45
 **/
@Component
@AllArgsConstructor
public class TestService implements ITestService {
    private ITestMapper testMapper;

    @Override
    public Test getById(Long id) {
        return testMapper.selectById(id);
    }
}
