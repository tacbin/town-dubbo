package com.tacbin.town.web.util;

import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.web.config.shiro.realm.MySimpleByteSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 15:04
 **/
@Component
public class UserInfoBeanUtil {
    @Value("${custom.md-times}")
    private String times;

    public UserInfo getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        return (UserInfo) subject.getPrincipal();
    }

    public String decodePwd(UserInfo info, String salt) {
        //将原始密码加盐（上面生成的盐），并且用md5算法加密多次，将最后结果存入数据库中
        return new SimpleHash("MD5", info.getPassword(), new MySimpleByteSource(salt), Integer.parseInt(times)).toHex();
    }

    public String decodePwd(String pwd, String salt) {
        //将原始密码加盐（上面生成的盐），并且用md5算法加密多次，将最后结果存入数据库中
        return new SimpleHash("MD5", pwd, new MySimpleByteSource(salt), Integer.parseInt(times)).toHex();
    }

    public String genSalt() {
        //生成盐（需要存入数据库中）
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }
}
