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
 * @Date : 2020-06-27 12:20
 **/
@Data
public class ProductOrderVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;

    private String location;

    private String name;

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
}
