package com.tacbin.town.repo.service;

import com.tacbin.town.repo.entity.UserPermissionLevel;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 12:18
 **/
public interface IUserPermissionService {
    UserPermissionLevel selectOneById(long id);
}
