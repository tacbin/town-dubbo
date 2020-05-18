package com.tacbin.town.common.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 11:10
 **/
public class ImageBuilder extends TextBuilder {
    @Override
    public WxMpXmlOutImageMessage build(String mediaId, WxMpXmlMessage wxMessage, WxMpService service) {
        return WxMpXmlOutMessage.IMAGE().mediaId(mediaId).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
    }
}
