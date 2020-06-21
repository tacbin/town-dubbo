package com.tacbin.town.api.service.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-14 15:33
 **/
@Data
public class ProductDataEntity implements Serializable {
    private String ip;

    private String device;

    private String location;

    private String osName;

    private long productId;

    // 默认字段
    private long id;

    private Date createTime;

    private Date modifyTime;

    private String enable;

    private String createId;

    private String modifyId;

    private int queue;

    private String description;

    // 非表字段
    private String locationCount;
}
