package com.tacbin.weixin.web.controller;

import com.tacbin.weixin.common.constants.AppConstants;
import com.tacbin.weixin.common.entity.ResponseInfo;
import com.tacbin.weixin.common.entity.Status;
import com.tacbin.weixin.service.TokenCheckService;
import com.tacbin.weixin.web.config.MpRouterConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tacbin
 * @createTime 2020/5/11 10:36
 * @description
 **/
@RestController
@Slf4j
@RequestMapping("/wx/{appId}")
public class TokenValidController {
    @Reference
    private TokenCheckService tokenCheckService;
    private MpRouterConfig mpRouterConfig;

    /**
     * token 校验
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping
    public String authGet(@PathVariable String appId,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        ResponseInfo<String> response = tokenCheckService.tokenCheck(appId, signature, timestamp, nonce, echostr);
        log.info("微信校验 result:{}", response.getData());
        return response.getData();
    }

    @PostMapping
    public String post(@PathVariable String appId,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info("\n接收到来自微信服务器的请求消息：[{}, {}, {}]", signature, timestamp, nonce);
        // 最后一个参数可随意填写
        ResponseInfo<String> response = tokenCheckService.tokenCheck(appId, signature, timestamp, nonce, AppConstants.APP_ID);
        if (response.getStatus() != Status.SUCCESS) {
            // 非法请求
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        // 添加到router中
        mpRouterConfig.mpServiceAddToRouter(appId);
        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage, appId);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if (AppConstants.ENCODING_AES.equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wrapMpConfigStorage(appId),
                    timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage, appId);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wrapMpConfigStorage(appId));
        }

        log.debug("\n组装回复信息：{}", out);
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message, String appId) {
        try {
            return mpRouterConfig.route(message, appId);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }

    private WxMpDefaultConfigImpl wrapMpConfigStorage(String appId) {
        WxMpDefaultConfigImpl defaultConfig = new WxMpDefaultConfigImpl();
        defaultConfig.setToken(AppConstants.VALID_TOKEN);
        defaultConfig.setAesKey(AppConstants.ENCODING_AES_KEY);
        defaultConfig.setAppId(AppConstants.APP_ID);
        defaultConfig.setSecret(AppConstants.SECRET_ID);
        return defaultConfig;
    }


    @Autowired
    public void setMpRouterConfig(MpRouterConfig mpRouterConfig) {
        this.mpRouterConfig = mpRouterConfig;
    }
}
