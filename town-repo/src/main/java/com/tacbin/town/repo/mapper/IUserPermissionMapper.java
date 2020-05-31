package com.tacbin.town.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tacbin.town.repo.entity.UserPermissionLevel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 12:17
 **/
@Mapper
public interface IUserPermissionMapper extends BaseMapper<UserPermissionLevel> {
}
