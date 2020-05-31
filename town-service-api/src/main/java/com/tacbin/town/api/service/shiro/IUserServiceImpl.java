package com.tacbin.town.api.service.shiro;

import com.tacbin.town.api.service.entity.UserInfo;

public interface IUserServiceImpl {
    UserInfo queryUserInfoByNameAndPwd(String name, String pwd);

    UserInfo queryUserInfoByName(String name);
}
