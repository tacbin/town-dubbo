package com.tacbin.town.repo.service.impl;

import com.tacbin.town.repo.entity.UserPermissionLevel;
import com.tacbin.town.repo.mapper.IUserPermissionMapper;
import com.tacbin.town.repo.service.IUserPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:54
 **/
@Service
@AllArgsConstructor
public class IUserPermissionServiceImpl implements IUserPermissionService {
    private IUserPermissionMapper userPermissionMapper;

    public UserPermissionLevel selectOneById(long id) {
        return userPermissionMapper.selectById(id);
    }
}
