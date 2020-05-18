package com.tacbin.town.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author tacbin
 * @createTime 2020/5/11 10:50
 * @description
 **/
@Getter
@Setter
@Builder
public class ResponseInfo<T> implements Serializable {
    private String message;

    private Status status;

    private T data;

    public ResponseInfo(String message, Status status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
