package com.tacbin.town.api.service.shiro;

import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.common.entity.ResponseInfo;

public interface IUserService {
    UserInfo queryUserInfoByNameAndPwd(String name, String pwd);

    UserInfo queryUserInfoByName(String name);

    ResponseInfo saveNewUser(UserInfo userInfo);

    void updateUserById(UserInfo user);

    UserInfo queryUserInfoById(long id);
}
