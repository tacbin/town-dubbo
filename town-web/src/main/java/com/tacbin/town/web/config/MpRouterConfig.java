package com.tacbin.town.web.config;

import com.tacbin.town.common.constants.AppConstants;
import com.tacbin.town.web.handler.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.CustomerService.*;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.POI_CHECK_NOTIFY;

/**
 * @author tacbin
 * @createTime 2020/5/7 10:55
 * @description
 **/
@Slf4j
@Component
public class MpRouterConfig {
    private ConcurrentHashMap<String, WxMpMessageRouter> map = new ConcurrentHashMap<>();

    private LogHandler logHandler;
    private NullHandler nullHandler;
    private KfSessionHandler kfSessionHandler;
    private StoreCheckNotifyHandler storeCheckNotifyHandler;
    private LocationHandler locationHandler;
    private MenuHandler menuHandler;
    private MsgHandler msgHandler;
    private UnsubscribeHandler unsubscribeHandler;
    private SubscribeHandler subscribeHandler;
    private ScanHandler scanHandler;

    /**
     * service添加到router
     *
     * @param appName
     */
    public void mpServiceAddToRouter(String appName) {
        String routeName = appName + AppConstants.ROUTER;
        if (map.containsKey(routeName)) {
            return;
        }
        // 设置参数值
        WxMpService service = new WxMpServiceImpl();
        WxMpDefaultConfigImpl defaultConfig = new WxMpDefaultConfigImpl();
        defaultConfig.setAppId(AppConstants.APP_ID);
        defaultConfig.setSecret(AppConstants.SECRET_ID);
        defaultConfig.setToken(AppConstants.VALID_TOKEN);
        service.setWxMpConfigStorage(defaultConfig);
        // 配置到router
        WxMpMessageRouter mpMessageRouter = createRouter(service);
        map.put(routeName, mpMessageRouter);
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, String appName) {
        String routeName = appName + AppConstants.ROUTER;
        WxMpMessageRouter mpMessageRouter = map.get(routeName);
        if (mpMessageRouter == null) {
            log.error("{} 的路由器异常", appName);
            return null;
        }
        return mpMessageRouter.route(wxMessage);
    }

    private WxMpMessageRouter createRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION)
                .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(EVENT).event(KF_CLOSE_SESSION)
                .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(EVENT).event(KF_SWITCH_SESSION)
                .handler(this.kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(EVENT).event(POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.LOCATION).handler(this.locationHandler).end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(this.locationHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.SCAN).handler(this.scanHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }

    public ConcurrentHashMap<String, WxMpMessageRouter> getMap() {
        return map;
    }

    @Autowired
    public void setLogHandler(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    @Autowired
    public void setNullHandler(NullHandler nullHandler) {
        this.nullHandler = nullHandler;
    }

    @Autowired
    public void setKfSessionHandler(KfSessionHandler kfSessionHandler) {
        this.kfSessionHandler = kfSessionHandler;
    }

    @Autowired
    public void setStoreCheckNotifyHandler(StoreCheckNotifyHandler storeCheckNotifyHandler) {
        this.storeCheckNotifyHandler = storeCheckNotifyHandler;
    }

    @Autowired
    public void setLocationHandler(LocationHandler locationHandler) {
        this.locationHandler = locationHandler;
    }

    @Autowired
    public void setMenuHandler(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Autowired
    public void setMsgHandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    @Autowired
    public void setUnsubscribeHandler(UnsubscribeHandler unsubscribeHandler) {
        this.unsubscribeHandler = unsubscribeHandler;
    }

    @Autowired
    public void setSubscribeHandler(SubscribeHandler subscribeHandler) {
        this.subscribeHandler = subscribeHandler;
    }

    @Autowired
    public void setScanHandler(ScanHandler scanHandler) {
        this.scanHandler = scanHandler;
    }
}
