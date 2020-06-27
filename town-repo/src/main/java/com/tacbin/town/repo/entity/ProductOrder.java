package com.tacbin.town.repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-27 12:16
 **/
@TableName("product_order")
@Getter
@Setter
public class ProductOrder {
    @TableId(value = "PRODUCT_ID")
    private long productId;

    @TableField(value = "LOCATION")
    private String location;

    @TableField(value = "NAME")
    private String name;

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
