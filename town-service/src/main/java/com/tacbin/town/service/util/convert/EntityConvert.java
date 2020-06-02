package com.tacbin.town.service.util.convert;

import com.tacbin.town.api.service.entity.PermissionLevel;
import com.tacbin.town.repo.entity.UserInfo;
import com.tacbin.town.repo.entity.UserPermissionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-01 21:37
 **/
@Slf4j
public class EntityConvert {
    public static void copyPermissionLevel(UserPermissionLevel source, PermissionLevel dest) {
        if (source == null) {
            dest = null;
            return;
        }
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("转换失败:{}", e.getMessage());
        }
    }

    public static void copyUserInfoRepoToApi(UserInfo source, com.tacbin.town.api.service.entity.UserInfo dest) {
        if (source == null) {
            dest = null;
            return;
        }
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("转换失败:{}", e.getMessage());
        }
    }

    public static void copyUserInfoApiToRepo(UserInfo dest, com.tacbin.town.api.service.entity.UserInfo source) {
        if (source == null) {
            dest = null;
            return;
        }
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("转换失败:{}", e.getMessage());
        }
    }
}
