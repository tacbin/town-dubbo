package com.tacbin.town.web.config.shiro.realm;

import com.tacbin.town.api.service.entity.PermissionLevel;
import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.api.service.shiro.IPermissionService;
import com.tacbin.town.api.service.shiro.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private IUserService IUserInfoService;

    @Autowired
    @Lazy
    private IPermissionService permissionService;

    public MyShiroRealm(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        PermissionLevel level = permissionService.selectOneById(userInfo.getPermissionId());
        authorizationInfo.addRole(level.getRole().toString());
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号 密码
        String userName = (String) token.getPrincipal();
        // 账号认证(将userName拿去数据库匹配数据)
        UserInfo userInfo = IUserInfoService.queryUserInfoByName(userName);
        if (userInfo.getId() == 0l) {
            log.warn("不存在名为{}的账号", userName);
            throw new AuthenticationException("账户不存在");
        }
        return new SimpleAuthenticationInfo(
                userInfo, //用户
                userInfo.getPassword(), //密码
                new MySimpleByteSource(userInfo.getSalt()),//salt=username+salt
                getName()  //realm name
        );
    }

}