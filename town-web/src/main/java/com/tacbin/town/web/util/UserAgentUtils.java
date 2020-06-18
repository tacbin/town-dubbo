package com.tacbin.town.web.util;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-14 9:52
 **/
@Slf4j
public class UserAgentUtils {

    private static UASparser uasParser = null;

    // 初始化uasParser对象
    static {
        try {
            uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserAgentInfo solveHttpRequest(HttpServletRequest request) {
        String content = request.getHeader("User-Agent");
        try {
//            System.out.println("操作系统名称：" + userAgentInfo.getOsFamily());
//            System.out.println("操作系统：" + userAgentInfo.getOsName());
//            System.out.println("浏览器名称：" + userAgentInfo.getUaFamily());
//            System.out.println("浏览器版本：" + userAgentInfo.getBrowserVersionInfo());
//            System.out.println("设备类型：" + userAgentInfo.getDeviceType());
//            System.out.println("浏览器:" + userAgentInfo.getUaName());
//            System.out.println("类型：" + userAgentInfo.getType());
            return uasParser.parse(content);
        } catch (IOException e) {
            log.error("解析失败:{}", e.getMessage());
            return null;
        }

    }
}
