package com.tacbin.town.service.service.impl.shiro;

import com.tacbin.town.api.service.shiro.IUserServiceImpl;
import com.tacbin.town.repo.entity.UserInfo;
import com.tacbin.town.repo.service.impl.IUserInfoServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.dubbo.config.annotation.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 22:57
 **/
@Service
@AllArgsConstructor
public class UserInfoServiceImpl implements IUserServiceImpl {
    private IUserInfoServiceImpl serviceImpl;

    public com.tacbin.town.api.service.entity.UserInfo queryUserInfoByNameAndPwd(String name, String pwd) {
        UserInfo userInfo = serviceImpl.queryUserInfoByNameAndPwd(name, pwd);
        com.tacbin.town.api.service.entity.UserInfo apiUserInfo = new com.tacbin.town.api.service.entity.UserInfo();
        copyUserInfo(userInfo, apiUserInfo);
        return apiUserInfo;
    }

    @Override
    public com.tacbin.town.api.service.entity.UserInfo queryUserInfoByName(String name) {
        UserInfo userInfo = serviceImpl.queryUserInfoByName(name);
        com.tacbin.town.api.service.entity.UserInfo apiUserInfo = new com.tacbin.town.api.service.entity.UserInfo();
        copyUserInfo(userInfo, apiUserInfo);
        return apiUserInfo;
    }

    private void copyUserInfo(UserInfo source, com.tacbin.town.api.service.entity.UserInfo dest) {
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
