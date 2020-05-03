package com.tacbin.weixin.web.controller.message;

import com.tacbin.weixin.web.handler.MsgHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ReplyMessageController {
    private MsgHandler msgHandler;
    private HttpServletRequest request;

    /**
     * 公众号发送消息给用户，前提是24小时之内有交互
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public String sendMessage(Object object) {
        System.out.println(object.toString());
        return "got it";
    }
}
