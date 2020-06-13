package com.tacbin.town.api.service.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-06 12:34
 **/
@Data
public class ProductEntity implements Serializable {
    private Long id;

    private Long userID;

    private String name;

    private Long categoryId;

    private int viewCount;

    private BigDecimal price;

    private String description;

    private String img1;

    private String img2;

    private String img3;

    private Date createTime;

    private Date modifyTime;

    private String enable;

    private String createId;

    private String modifyId;

    private int queue;
}
