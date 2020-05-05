package com.tacbin.weixin.web.config.filter;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.weixin.common.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

/**
 * @Description :web过滤器
 * @Author : Administrator
 * @Date : 2020-05-05 11:23
 **/
@Component
@WebFilter(filterName = "authcFilter", urlPatterns = "/*")
@Slf4j
@AllArgsConstructor
public class AuthcFilter implements Filter {
    private WxMpService wxService;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ShiroHttpServletRequest shiroHttpServletRequest = (ShiroHttpServletRequest) servletRequest;
        log.info("请求url:{} 参数:{}", shiroHttpServletRequest.getPathInfo(), shiroHttpServletRequest.getParameterMap().toString());
        if (validAuthQuest(shiroHttpServletRequest.getParameterMap())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletResponse.getWriter().write(new ResponseInfo<>("", Status.FAIL, "未认证请求").toString());
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 是否为认证请求
     */
    private boolean validAuthQuest(Map<String, String[]> questMap) {
        try {
            String code = questMap.get("code")[0];
            WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);
            WxMpUser user = wxService.oauth2getUserInfo(accessToken, null);
            log.info("认证的 user info：{}", user);
        } catch (WxErrorException e) {
            log.error("获取信息失败：{}", e.getMessage());
            return false;
        }
        return true;
    }
}
