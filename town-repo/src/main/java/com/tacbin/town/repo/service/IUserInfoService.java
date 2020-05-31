package com.tacbin.town.repo.service;

import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.repo.entity.UserInfo;

public interface IUserInfoService {

    UserInfo queryUserInfoByNameAndPwd(String name, String pwd);

    UserInfo queryUserInfoByName(String name);

    ResponseInfo saveNewUser(UserInfo userInfo);

    void updateUserById(UserInfo user);

    UserInfo queryUserInfoById(long id);

}
