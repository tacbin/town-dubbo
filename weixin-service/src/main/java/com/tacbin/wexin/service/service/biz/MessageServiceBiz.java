package com.tacbin.wexin.service.service.biz;

import com.tacbin.weixin.common.builder.ImageBuilder;
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
public class MessageServiceBiz {
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
        return new ImageBuilder().build(mediaId, wxMessage, weixinService);
    }
}
