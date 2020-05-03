package com.tacbin.weixin.web.controller;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 16:55
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/menu/{appid}")
public class MenuController {
    private WxMpServiceImpl wxMpService;

    /**
     * 创建自定义菜单
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String menuCreateSample(@PathVariable String appid) throws WxErrorException, MalformedURLException {
        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.MenuButtonType.CLICK);
        button1.setName("今日歌曲");
        button1.setKey("V1001_TODAY_MUSIC");

//        WxMenuButton button2 = new WxMenuButton();
//        button2.setType(WxConsts.BUTTON_MINIPROGRAM);
//        button2.setName("小程序");
//        button2.setAppId("wx286b93c14bbf93aa");
//        button2.setPagePath("pages/lunar/index.html");
//        button2.setUrl("http://mp.weixin.qq.com");

        WxMenuButton button3 = new WxMenuButton();
        button3.setName("菜单");

        menu.getButtons().add(button1);
//        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        WxMenuButton button31 = new WxMenuButton();
        button31.setType(WxConsts.MenuButtonType.VIEW);
        button31.setName("搜索");
        button31.setUrl("http://www.soso.com/");

        WxMenuButton button32 = new WxMenuButton();
        button32.setType(WxConsts.MenuButtonType.VIEW);
        button32.setName("视频");
        button32.setUrl("http://v.qq.com/");

        WxMenuButton button33 = new WxMenuButton();
        button33.setType(WxConsts.MenuButtonType.CLICK);
        button33.setName("赞一下我们");
        button33.setKey("V1001_GOOD");

        WxMenuButton button34 = new WxMenuButton();
        button34.setType(WxConsts.MenuButtonType.VIEW);
        button34.setName("获取用户信息");

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            URL requestURL = new URL(request.getRequestURL().toString());
            String url = this.wxMpService.switchoverTo(appid).oauth2buildAuthorizationUrl(
                    String.format("%s://%s/wx/redirect/%s/greet", requestURL.getProtocol(), requestURL.getHost(), appid),
                    WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
            button34.setUrl(url);
        }

        button3.getSubButtons().add(button31);
        button3.getSubButtons().add(button32);
        button3.getSubButtons().add(button33);
        button3.getSubButtons().add(button34);

        this.wxMpService.switchover(appid);
        return this.wxMpService.getMenuService().menuCreate(menu);
    }

    /**
     * 获取自定义菜单
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Object getMenu() {
        try {
            return new ResponseInfo<>(null, Status.SUCCESS, wxMpService.getMenuService().menuGet());
        } catch (WxErrorException e) {
            log.error("error:{}", e);
            return new ResponseInfo<>("创建失败!", Status.FAIL, e.getMessage());
        }
    }
}
