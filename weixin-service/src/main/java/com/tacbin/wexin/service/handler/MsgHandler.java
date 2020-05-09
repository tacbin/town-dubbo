package com.tacbin.wexin.service.handler;


import com.tacbin.weixin.common.builder.TextBuilder;
import com.tacbin.weixin.common.utils.JsonUtils;
import com.tacbin.wexin.service.service.biz.MessageServiceBiz;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@AllArgsConstructor
@Slf4j
public class MsgHandler extends AbstractHandler {
    private MessageServiceBiz messageServiceBiz;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        // 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                logger.info("-- 开始转发 --");
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            logger.info("客服连接失败：{}", e.getMessage());
        }
        // 持久化消息
        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }
        return dealDifferentTypeOfMessage(wxMessage, weixinService);
    }

    /**
     * 不同消息类型进行处理
     */
    private WxMpXmlOutMessage dealDifferentTypeOfMessage(WxMpXmlMessage wxMessage, WxMpService weixinService) {
        // 不同类型的消息进行处理
        switch (wxMessage.getMsgType()) {
            case XmlMsgType.TEXT:
                return messageServiceBiz.replyTextMessage(wxMessage.getContent(), wxMessage, weixinService);
            case XmlMsgType.IMAGE:
                return messageServiceBiz.replyImageMessage(wxMessage.getMediaId(), wxMessage, weixinService);
            default:
                return new TextBuilder().build(JsonUtils.toJson(wxMessage), wxMessage, weixinService);
        }
    }
}