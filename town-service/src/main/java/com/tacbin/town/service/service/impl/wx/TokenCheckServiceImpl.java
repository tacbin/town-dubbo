package com.tacbin.town.service.service.impl.wx;

import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.api.service.TokenCheckService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tacbin
 * @createTime 2020/5/11 10:59
 * @description
 **/
@Service
@Slf4j
public class TokenCheckServiceImpl implements TokenCheckService {
    private WxMpServiceBiz mpServiceBiz;

    @Override
    public ResponseInfo<String> tokenCheck(String appId, String signature, String timestamp, String nonce, String echostr) {
        WxMpService wxService = mpServiceBiz.getService(appId);
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        // 是否包含空白字符
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            return new ResponseInfo<>("包含空白字符串", Status.FAIL, null);
        }
        // 校验
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return new ResponseInfo<>("", Status.SUCCESS, echostr);
        }
        log.error("校验失败：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        return new ResponseInfo<>("校验失败", Status.FAIL, null);
    }

    @Autowired
    public void setWxMpServiceBiz(WxMpServiceBiz wxMpServiceBiz) {
        this.mpServiceBiz = wxMpServiceBiz;
    }
}
