package com.tacbin.town.repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description :商品类别表
 * @Author : Administrator
 * @Date : 2020-03-01 11:52
 **/
@TableName("category")
@Getter
@Setter
public class Category implements Serializable {
    @TableField("NAME")
    private String name;

    @TableField("USERID")
    private long userID;

    // 非表字段
    @TableField(exist = false)
    private int count;

    @TableField(exist = false)
    List<Product> productList;

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
