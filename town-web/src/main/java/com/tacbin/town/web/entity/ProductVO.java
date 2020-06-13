package com.tacbin.town.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-06 12:28
 **/
@Data
public class ProductVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userID;

    private String name;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    private int viewCount;

    private BigDecimal price;

    private String description;

    private String img1;

    private String img2;

    private String img3;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    private String enable;

    private String createId;

    private String modifyId;

    private int queue;
}
