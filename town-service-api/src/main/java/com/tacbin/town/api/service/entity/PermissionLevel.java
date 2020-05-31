package com.tacbin.town.api.service.entity;

import com.tacbin.town.common.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 22:51
 **/
@Data
public class PermissionLevel implements Serializable {
    private int level;
    private long id;
    private Date createTime;
    private Date modifyTime;
    private String enable;
    private String createId;
    private String modifyId;
    private int queue;
    private String description;
    private Role role;
}
