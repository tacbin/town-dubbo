package com.tacbin.town.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-14 15:34
 **/
@Data
public class ProductDataVO implements Serializable {
    private String ip;

    private String device;

    private String location;

    private String osName;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;

    // 默认字段
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date modifyTime;

    private String enable;

    private String createId;

    private String modifyId;

    private int queue;

    private String description;

    // 非表字段
    private String locationCount;
}
