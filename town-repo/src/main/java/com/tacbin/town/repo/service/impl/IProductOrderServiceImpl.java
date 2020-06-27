package com.tacbin.town.repo.service.impl;

import com.tacbin.town.repo.entity.ProductOrder;
import com.tacbin.town.repo.mapper.ProductOrderMapper;
import com.tacbin.town.repo.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-27 12:35
 **/
@Service
public class IProductOrderServiceImpl implements IProductOrderService {
    private ProductOrderMapper orderMapper;

    @Override
    public void insertOrder(ProductOrder order) {
        orderMapper.insert(order);
    }

    @Autowired
    public void setOrderMapper(ProductOrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
}
