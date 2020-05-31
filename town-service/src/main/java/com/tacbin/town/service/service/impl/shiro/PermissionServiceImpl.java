package com.tacbin.town.service.service.impl.shiro;

import com.tacbin.town.api.service.entity.PermissionLevel;
import com.tacbin.town.api.service.shiro.IPermissionService;
import com.tacbin.town.repo.entity.UserPermissionLevel;
import com.tacbin.town.repo.service.impl.IUserPermissionServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.dubbo.config.annotation.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 23:14
 **/
@Service
@AllArgsConstructor
public class PermissionServiceImpl implements IPermissionService {
    private IUserPermissionServiceImpl permissionService;

    @Override
    public PermissionLevel selectOneById(long id) {
        UserPermissionLevel permissionLevel = permissionService.selectOneById(id);
        PermissionLevel level = new PermissionLevel();
        copyPermissionLevel(permissionLevel, level);
        return level;
    }

    private void copyPermissionLevel(UserPermissionLevel source, PermissionLevel dest) {
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
