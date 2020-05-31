package com.tacbin.town.api.service.shiro;


import com.tacbin.town.api.service.entity.PermissionLevel;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 23:13
 **/
public interface IPermissionService {
    PermissionLevel selectOneById(long id);
}
