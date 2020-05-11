package com.tacbin.wexin.service.service.biz;

import com.tacbin.weixin.common.constants.AppConstants;
import lombok.Setter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceOkHttpImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tacbin
 * @createTime 2020/5/11 10:20
 * @description
 **/
@Component
public class WxMpServiceBiz {
    private JedisPool jedisPool;
    private ConcurrentHashMap<String, WxMpService> serviceMap = new ConcurrentHashMap<>();

    public WxMpService getService(String appId) {
        if (serviceMap.get(appId) == null) {
            synchronized (this) {
                if (serviceMap.get(appId) == null) {
                    serviceMap.put(appId, createService(appId));
                }
            }
        }
        return serviceMap.get(appId);
    }

    private WxMpService createService(String appId) {
        WxMpServiceOkHttpImpl service = new WxMpServiceOkHttpImpl();
        WxMpRedisConfigImpl conf = new WxMpRedisConfigImpl(jedisPool);
        // 写死一个
        conf.setAppId(AppConstants.APP_ID);
        conf.setSecret(AppConstants.SECRET_ID);
        conf.setToken(AppConstants.VALID_TOKEN);
        service.setWxMpConfigStorage(conf);
        return service;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
