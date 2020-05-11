package com.tacbin.weixin.service;

import com.tacbin.weixin.common.entity.ResponseInfo;

/**
 * @author tacbin
 */
public interface TokenCheckService {
    /**
     * token校验
     *
     * @param appId
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    ResponseInfo<String> tokenCheck(String appId, String signature, String timestamp, String nonce, String echostr);
}
