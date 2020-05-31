package com.tacbin.town.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-31 19:53
 **/
@Data
public class TestEntityVO implements Serializable {
    private String name;
    private String comment;
}
