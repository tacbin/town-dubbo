package com.tacbin.town.api.service.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 19:50
 **/
@Data
public class TestEntity implements Serializable {
    private String name;
    private String comment;
}
