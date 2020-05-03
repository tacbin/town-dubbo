package com.tacbin.weixin.web.entity.request;

import lombok.Data;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 9:10
 **/
@Data
public class MessageRequest {
    private String toUserName;
    private String content ;
}
