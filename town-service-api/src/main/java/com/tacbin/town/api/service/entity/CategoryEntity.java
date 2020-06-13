package com.tacbin.town.api.service.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-06 12:33
 **/
@Data
public class CategoryEntity implements Serializable {
    private String name;

    // 非表字段
    private int count;
    private Long userID;

    List<ProductEntity> productList;

    // 默认字段
    private Long id;

    private Date createTime;

    private Date modifyTime;

    private String enable;

    private String createId;

    private String modifyId;

    private int queue;

    private String description;
}
