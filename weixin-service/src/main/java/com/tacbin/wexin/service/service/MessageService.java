package com.tacbin.wexin.service.service;

import com.tacbin.weixin.common.builder.TextBuilder;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 9:54
 **/
@Service
public class MessageService {
    /**
     * 回复文本消息
     */
    public WxMpXmlOutMessage replyTextMessage(String content, WxMpXmlMessage wxMessage, WxMpService weixinService) {
        return new TextBuilder().build("收到" + content, wxMessage, weixinService);
    }

    /**
     * 回复图片消息
     */
    public WxMpXmlOutMessage replyImageMessage(String mediaId, WxMpXmlMessage wxMessage, WxMpService weixinService) {
        return new TextBuilder().build(mediaId, wxMessage, weixinService);
    }
}
