package com.tacbin.town.web.config.shiro;

import com.tacbin.town.web.config.shiro.realm.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 授权，认证，拦截器配置
 */
@Configuration
public class ShiroAuthFilterConfig {
    @Value("${custom.md-times}")
    private String times;

    /**
     * 认证,授权管理
     **/
    @Bean
    public Realm realm(RedisCacheManager cacheManager) {
        MyShiroRealm realm = new MyShiroRealm(cacheManager);
        // 开启缓存
        realm.setAuthenticationCachingEnabled(true);
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /**
     * AbstractShiroWebFilterConfiguration 查看未授权和未登录的地址怎么设置
     * 拦截器管理
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // 登录页，登录接口
        chainDefinition.addPathDefinition("/customer/**", "anon");
        chainDefinition.addPathDefinition("/user/isLogin", "anon");
        chainDefinition.addPathDefinition("/user/login", "anon");
        // swagger
        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        // 其余页面都需登录
        chainDefinition.addPathDefinition("/**", "authc");//authc
        return chainDefinition;
    }


    /**
     * 加密算法用的
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列的次数
        hashedCredentialsMatcher.setHashIterations(Integer.parseInt(times));
        // 散列算法
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        return hashedCredentialsMatcher;
    }

}