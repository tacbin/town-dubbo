package com.tacbin.town.service.service.impl.shiro;

import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.api.service.shiro.IUserService;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.repo.service.impl.IUserInfoServiceImpl;
import com.tacbin.town.service.util.convert.EntityConvert;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-01 21:32
 **/
@Service
@AllArgsConstructor
public class IUserServiceImpl implements IUserService {
    private IUserInfoServiceImpl userInfoService;

    @Override
    public UserInfo queryUserInfoByNameAndPwd(String name, String pwd) {
        UserInfo userInfo = new UserInfo();
        EntityConvert.copyUserInfoRepoToApi(userInfoService.queryUserInfoByNameAndPwd(name, pwd), userInfo);
        return userInfo;
    }

    @Override
    public UserInfo queryUserInfoByName(String name) {
        UserInfo userInfo = new UserInfo();
        EntityConvert.copyUserInfoRepoToApi(userInfoService.queryUserInfoByName(name), userInfo);
        return userInfo;
    }

    @Override
    public ResponseInfo saveNewUser(UserInfo userInfo) {
        com.tacbin.town.repo.entity.UserInfo user = new com.tacbin.town.repo.entity.UserInfo();
        EntityConvert.copyUserInfoApiToRepo(user, userInfo);
        return userInfoService.saveNewUser(user);
    }

    @Override
    public void updateUserById(UserInfo userInfo) {
        com.tacbin.town.repo.entity.UserInfo user = new com.tacbin.town.repo.entity.UserInfo();
        EntityConvert.copyUserInfoApiToRepo(user, userInfo);
        userInfoService.updateUserById(user);
    }

    @Override
    public UserInfo queryUserInfoById(long id) {
        com.tacbin.town.repo.entity.UserInfo user = userInfoService.queryUserInfoById(id);
        UserInfo userInfo = new UserInfo();
        EntityConvert.copyUserInfoRepoToApi(user, userInfo);
        return userInfo;
    }
}
