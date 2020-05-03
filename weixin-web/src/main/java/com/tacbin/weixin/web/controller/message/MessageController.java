package com.tacbin.weixin.web.controller.message;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.weixin.web.entity.request.MessageRequest;
import com.tacbin.wexin.service.handler.MsgHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 8:49
 **/
@Slf4j
@RestController
@RequestMapping("/msg")
@AllArgsConstructor
public class MessageController {
    private MsgHandler msgHandler;
    private HttpServletRequest request;

    /**
     * api测试
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public ResponseInfo<String> sendMessage(@RequestBody MessageRequest messageRequest) {
        return new ResponseInfo<>("收到！", Status.SUCCESS, messageRequest.toString());
    }
}
