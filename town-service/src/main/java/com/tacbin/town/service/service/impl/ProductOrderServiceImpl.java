package com.tacbin.town.service.service.impl;

import com.tacbin.town.api.service.ProductOrderService;
import com.tacbin.town.api.service.entity.ProductOrderEntity;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.repo.entity.ProductOrder;
import com.tacbin.town.repo.service.IProductOrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-27 12:33
 **/
@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    @Autowired
    private IProductOrderService orderService;

    @Override
    public void insertOrder(ProductOrderEntity entity) {
        ProductOrder productOrder = new ProductOrder();
        PropertiesConvert.copyObjectRepoToApi(entity, productOrder);
        orderService.insertOrder(productOrder);
    }
}
