package com.tacbin.town.repo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.repo.entity.UserInfo;
import com.tacbin.town.repo.mapper.IUserInfoMapper;
import com.tacbin.town.repo.service.IUserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:54
 **/
@Service
@AllArgsConstructor
public class IUserInfoServiceImpl implements IUserInfoService {
    private IUserInfoMapper userInfoMapper;

    @Override
    public UserInfo queryUserInfoByNameAndPwd(String name, String pwd) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserName, name).eq(UserInfo::getPassword, pwd);
        return userInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public UserInfo queryUserInfoByName(String name) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserName, name);
        return userInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public ResponseInfo saveNewUser(UserInfo userInfo) {
        if (StringUtils.isEmpty(userInfo.getUserName()) || StringUtils.isEmpty(userInfo.getPassword())) {
            return ResponseInfo.builder().message("请填写账户名或者密码").status(Status.FAIL).build();
        }
        UserInfo user = queryUserInfoByName(userInfo.getUserName());
        if (user != null) {
            return ResponseInfo.builder().message("已存在该用户名").status(Status.FAIL).build();
        }
        int value = userInfoMapper.insert(userInfo);
        return value == 1 ? ResponseInfo.builder().message("注册成功").status(Status.SUCCESS).build() : ResponseInfo.builder().message("已停止注册!").status(Status.FAIL).build();
    }

    @Override
    public void updateUserById(UserInfo user) {
        userInfoMapper.updateById(user);
    }

    @Override
    public UserInfo queryUserInfoById(long id) {
        return userInfoMapper.selectById(id);
    }
}
