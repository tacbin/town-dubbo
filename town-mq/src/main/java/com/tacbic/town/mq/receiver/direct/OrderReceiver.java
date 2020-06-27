package com.tacbic.town.mq.receiver.direct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tacbin.town.api.service.ProductOrderService;
import com.tacbin.town.api.service.entity.ProductOrderEntity;
import com.tacbin.town.common.constants.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-27 9:24
 **/
@Component
@RabbitListener(queues = MqConstants.PRODUCT_ORDER_QUEUE)
@Slf4j
public class OrderReceiver {
    @Reference
    private ProductOrderService productOrderService;

    @RabbitHandler
    public void handler(String message) {
        try {
            log.info("收到mq消息{}", message);
            ProductOrderEntity entity = JSONObject.parseObject(message, ProductOrderEntity.class);
            productOrderService.insertOrder(entity);
        } catch (Exception e) {
            log.error("mq异常:{}", e.getMessage());
        }
    }
}
