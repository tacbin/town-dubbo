package com.tacbin.weixin.web.controller.message;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.weixin.web.entity.request.MessageRequest;
import com.tacbin.wexin.service.handler.MsgHandler;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
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
    private WxMpService mpService;

    /**
     * api测试
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    @ApiOperation(value = "发送信息", notes = "发送信息")
    public ResponseInfo<String> sendMessage() {
        WxMpKefuService kefuService = mpService.getKefuService();
        try {
            kefuService.sendKefuMessage(new WxMpKefuMessage());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return new ResponseInfo<>("收到！", Status.SUCCESS, "");
    }
}
