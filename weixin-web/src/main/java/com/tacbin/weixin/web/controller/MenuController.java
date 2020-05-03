package com.tacbin.weixin.web.controller;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 16:55
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {
    private WxMpServiceImpl wxMpService;

    /**
     * 创建自定义菜单
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseInfo<String> createMenu(@RequestBody WxMenu wxMenu) {
        try {
            wxMpService.getMenuService().menuCreate(wxMenu);
            return new ResponseInfo<>("创建成功!", Status.SUCCESS, null);
        } catch (WxErrorException e) {
            log.error("error{}", e.getMessage());
            return new ResponseInfo<>("创建失败!", Status.FAIL, e.getMessage());
        }
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
