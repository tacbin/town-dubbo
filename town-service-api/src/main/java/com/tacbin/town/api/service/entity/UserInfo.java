package com.tacbin.town.api.service.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 22:51
 **/
@Data
public class UserInfo implements Serializable {
    private long id;

    private long permissionId;

    private String userName;

    private String password;

    private String loginIp;

    private String salt;

    private Date createTime;

    private Date modifyTime;

    private String enable;

    private String createId;

    private String modifyId;

    private int queue;

    private String description;
}
