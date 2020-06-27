package com.tacbin.town.web.controller.customer;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tacbin.town.api.service.ProductOrderService;
import com.tacbin.town.api.service.entity.ProductOrderEntity;
import com.tacbin.town.common.constants.MqConstants;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.entity.ProductOrderVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-27 12:21
 **/
@RestController
@RequestMapping("/order")
public class ProductOrderController {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    private Cache<String, String> orderIdCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @AnalysisLog
    @RequestMapping(path = "/newOrder", method = RequestMethod.POST)
    public ResponseInfo<Void> newOrder(ProductOrderVO productOrderVO) {
        String orderId = productOrderVO.getId() + "";
        String value = orderIdCache.getIfPresent(orderId);
        // 幂等
        if (!StringUtils.isEmpty(value)) {
            return new ResponseInfo<>("不允许重复下单", Status.FAIL, null);
        }
        ProductOrderEntity productOrderEntity = new ProductOrderEntity();
        PropertiesConvert.copyObjectRepoToApi(productOrderVO, productOrderEntity);
        // 削峰
        rabbitTemplate.convertAndSend(MqConstants.PRODUCT_ORDER_EXCHANGE, MqConstants.PRODUCT_ORDER_ROUTE_KEY, JSON.toJSONString(productOrderEntity));
        return new ResponseInfo<>("新增成功", Status.SUCCESS, null);
    }

    @AnalysisLog
    @RequestMapping(path = "/orderId", method = RequestMethod.POST)
    public ResponseInfo<String> getOrderId() {
        String orderId = SnowFlakeUtil.generateId() + "";
        return new ResponseInfo<>("生成订单成功", Status.SUCCESS, orderId);
    }
}
