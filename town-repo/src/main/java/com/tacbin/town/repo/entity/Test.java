package com.tacbin.town.repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 19:38
 **/
@TableName("test")
@Setter
@Getter
public class Test implements Serializable {
    @TableId(value = "id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("comment")
    private String comment;
}
