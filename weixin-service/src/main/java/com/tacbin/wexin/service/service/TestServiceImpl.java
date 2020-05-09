package com.tacbin.wexin.service.service;

import com.tacbin.weixin.service.TestService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author tacbin
 * @createTime 2020/5/9 10:03
 * @description
 **/
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "success";
    }
}
