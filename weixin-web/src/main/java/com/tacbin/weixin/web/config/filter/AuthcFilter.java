package com.tacbin.weixin.web.config.filter;

import com.tacbin.weixin.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Description :web过滤器
 * @Author : Administrator
 * @Date : 2020-05-05 11:23
 **/
@Component
@WebFilter(filterName = "authcFilter", urlPatterns = "/*")
@Slf4j
public class AuthcFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            log.info("请求:{}", JsonUtils.toJson(servletRequest));
        } catch (Exception e) {
            log.error("authcFilter error:{}", e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
