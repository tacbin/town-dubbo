package com.tacbin.town.repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description :商品表
 * @Author : Administrator
 * @Date : 2020-03-01 11:47
 **/
@TableName("products")
@Getter
@Setter
public class Product implements Serializable {
    @TableId("ID")
    private long id = genId();

    @TableField("USERID")
    private long userID;

    @TableField("NAME")
    private String name;

    @TableField("CATEGORY_ID")
    private long categoryId;

    @TableField("VIEW_COUNT")
    private int viewCount;

    @TableField("PRICE")
    private BigDecimal price;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("IMG1")
    private String img1;

    @TableField("IMG2")
    private String img2;

    @TableField("IMG3")
    private String img3;

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

    private long genId() {
        if (id == 0l) {
            return SnowFlakeUtil.generateId();
        }
        return id;
    }
}
