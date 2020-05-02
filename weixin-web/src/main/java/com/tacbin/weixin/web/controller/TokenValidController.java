package com.tacbin.weixin.web.controller;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-02 23:31
 **/
@RestController
@RequestMapping("/wx")
public class TokenValidController {

    @RequestMapping(path = "/valid", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseInfo<String> tokenValid() {
        return new ResponseInfo<>("校验成功", Status.SUCCESS, "success");
    }
}
