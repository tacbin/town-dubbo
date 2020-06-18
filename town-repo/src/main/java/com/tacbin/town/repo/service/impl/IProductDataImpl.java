package com.tacbin.town.repo.service.impl;

import com.tacbin.town.repo.entity.ProductData;
import com.tacbin.town.repo.mapper.IProductDataMapper;
import com.tacbin.town.repo.service.IProductDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-14 15:38
 **/
@Service
@AllArgsConstructor
public class IProductDataImpl implements IProductDataService {
    private IProductDataMapper productDataMapper;

    @Override
    public void productViewCount(ProductData productData) {
        productDataMapper.insert(productData);
    }
}
