package com.tacbin.town.api.service.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-27 12:19
 **/
@Data
public class ProductOrderEntity implements Serializable {
    private long productId;

    private String location;

    private String name;

    // 默认字段
    private long id;

    private Date createTime;

    private Date modifyTime;

    private String enable;

    private String createId;

    private String modifyId;

    private int queue;

    private String description;
}
