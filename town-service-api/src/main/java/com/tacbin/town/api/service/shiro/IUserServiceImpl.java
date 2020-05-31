package com.tacbin.town.api.service.shiro;

import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.common.entity.ResponseInfo;

public interface IUserServiceImpl {
    UserInfo queryUserInfoByNameAndPwd(String name, String pwd);

    UserInfo queryUserInfoByName(String name);

    ResponseInfo saveNewUser(UserInfo userInfo);
}
