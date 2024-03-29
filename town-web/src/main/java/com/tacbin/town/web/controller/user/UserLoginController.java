package com.tacbin.town.web.controller.user;

import com.google.common.cache.CacheBuilder;
import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.api.service.shiro.IUserService;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.config.shiro.realm.MyShiroRealm;
import com.tacbin.town.web.util.UserInfoBeanUtil;
import com.tacbin.town.web.util.ip.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-01 21:25
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserLoginController {
    @Reference
    private IUserService userService;

    private MyShiroRealm shiroRealm;

    private UserInfoBeanUtil userInfoBeanUtil;

    private final static int LOGIN_WAITING_TIME = 3;

    // 为true则几秒后再试，false或空则走校验
    private com.google.common.cache.Cache<String, Boolean> loginCache = CacheBuilder.newBuilder()
            .expireAfterWrite(LOGIN_WAITING_TIME, TimeUnit.SECONDS)
            .build();

    @AnalysisLog
    @RequestMapping(path = "/getUserId", method = RequestMethod.POST)
    public ResponseInfo<String> getUserId() {
        return new ResponseInfo<>("", Status.SUCCESS, userInfoBeanUtil.getCurrentUser().getId() + "");
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @AnalysisLog
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseInfo<Boolean> login(@RequestParam String userName, @RequestParam String password, HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        if (loginCache.getIfPresent(ip) != null) {
            return new ResponseInfo("还没过等待时间", Status.FAIL, null);
        } else if (isLogin().getData()) {
            return new ResponseInfo<>("登录成功", Status.SUCCESS, null);
        } else {
            return userLoginIn(userName, password, ip);
        }
    }

    /**
     * 修改密码
     *
     * @param newPassword
     * @param repeatPassword
     * @return
     */
    @AnalysisLog
    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    public ResponseInfo<String> changePassword(@RequestParam String newPassword, @RequestParam String repeatPassword) {
        try {
            changePwd(newPassword, repeatPassword);
        } catch (Exception e) {
            log.error("密码修改失败:{}", e.getMessage());
            return new ResponseInfo<>("修改失败", Status.FAIL, e.getMessage());
        }
        return new ResponseInfo<>("修改成功", Status.SUCCESS, null);
    }

    /**
     * 登出
     */
    @AnalysisLog
    @RequestMapping(method = RequestMethod.POST, value = "/loginOut")
    public void loginOut() {
        userInfoBeanUtil.logOut();
    }

    /**
     * 是否处于登录态
     *
     * @return
     */
    @AnalysisLog
    @RequestMapping(method = RequestMethod.POST, value = "/isLogin")
    public ResponseInfo<Boolean> isLogin() {
        return new ResponseInfo<>("", Status.SUCCESS, userInfoBeanUtil.isLogin());
    }

    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 密码
     */
    private ResponseInfo userLoginIn(String name, String password, String ip) {
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            // 会话管理
            sessionManager(subject);
        } catch (Exception e) {
            // 身份验证失败
            log.error(name + " 身份认证失败 ");
            loginCache.put(ip, true);
            return ResponseInfo.builder().message("登录失败,账号或密码错误。" + LOGIN_WAITING_TIME + "秒后再试").status(Status.FAIL).build();
        }
        return ResponseInfo.builder().message("登录成功").status(Status.SUCCESS).build();
    }

    /**
     * 会话管理
     *
     * @param subject
     */
    private void sessionManager(Subject subject) {
        UserInfo user = (UserInfo) subject.getPrincipal();
        Session session = subject.getSession();
        // 会话ID
        session.getId();
        // 访问者的主机
        if (StringUtils.isEmpty(session.getHost())) {
            String loginIp = session.getHost();
            user.setLoginIp(loginIp);
            userService.updateUserById(user);
        }
        // 最近访问
        session.getLastAccessTime();
        // 设置半小时超时
        session.setTimeout(1000 * 60 * 30);
    }

    /**
     * 修改密码
     */
    private void changePwd(String newPassword, String repeatPassword) throws Exception {
        if (StringUtils.isEmpty(newPassword)) {
            throw new Exception("新密码不能为空或者不能为空格");
        }
        if (!newPassword.equals(repeatPassword)) {
            throw new Exception("两次密码输入不一致");
        }
        UserInfo info = userInfoBeanUtil.getCurrentUser();
        String salt = userInfoBeanUtil.genSalt();
        info.setPassword(newPassword);
        info.setPassword(userInfoBeanUtil.decodePwd(newPassword, salt));
        info.setSalt(salt);
        userService.updateUserById(info);
        // 登出并清除缓存
        SecurityUtils.getSubject().logout();
        Cache<Object, AuthenticationInfo> cache = shiroRealm.getAuthenticationCache();
        if (cache != null) {
            cache.remove(info.getUserName());
        }
    }

    @Autowired
    public void setShiroRealm(MyShiroRealm shiroRealm) {
        this.shiroRealm = shiroRealm;
    }

    @Autowired
    public void setUserInfoBeanUtil(UserInfoBeanUtil userInfoBeanUtil) {
        this.userInfoBeanUtil = userInfoBeanUtil;
    }
}
