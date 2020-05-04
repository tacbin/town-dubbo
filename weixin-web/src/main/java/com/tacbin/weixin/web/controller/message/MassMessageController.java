package com.tacbin.weixin.web.controller.message;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMassMessageService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 19:31
 **/
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/mass-message")
public class MassMessageController {
    private WxMpService mpService;

    @RequestMapping(path = "/sendText", method = RequestMethod.POST)
    public void sendText(String openid) throws WxErrorException {
        // 发送群发消息
        WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent("测试群发消息\n欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");
        massMessage.getToUsers().add(openid);
        WxMpMassSendResult massResult = this.mpService.getMassMessageService()
                .massOpenIdsMessageSend(massMessage);
        assertNotNull(massResult);
        assertNotNull(massResult.getMsgId());
    }
}
