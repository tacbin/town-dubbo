package com.tacbin.town.repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description :用户信息拓展表
 * @Author : Administrator
 * @Date : 2020-03-13 18:37
 **/
@TableName("user_extend")
@Getter
@Setter
public class UserExtend {
    @TableField("USERID")
    private long userId;

    @TableField("SHOP_NAME")
    private String shopName;

    @TableField("EXPIRE_TIME")
    private Date expireTime;

    @TableField("NAME")
    private String name;

    @TableField("PHONE")
    private String phone;

    @TableField("WX_CHAT")
    private String wxChat;


    // 默认字段
    @TableId(value = "ID")
    private long id = genId();

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MODIFY_TIME")
    private Date modifyTime;

    @TableField("ENABLE")
    private String enable;

    @TableField("CREATE_ID")
    private String createId;

    @TableField("MODIFY_ID")
    private String modifyId;

    @TableField("QUEUE")
    private int queue;

    @TableField("DESCRIPTION")
    private String description;

    private long genId() {
        if (id == 0l) {
            return SnowFlakeUtil.generateId();
        }
        return id;
    }
}
