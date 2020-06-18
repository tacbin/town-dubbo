package com.tacbin.town.service.service.impl;

import com.tacbin.town.api.service.ProductDataService;
import com.tacbin.town.api.service.entity.ProductDataEntity;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.repo.entity.ProductData;
import com.tacbin.town.repo.mapper.IProductDataMapper;
import com.tacbin.town.repo.service.IProductDataService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-14 15:35
 **/
@Service
@AllArgsConstructor
public class ProductDataImpl implements ProductDataService {
    private IProductDataService productDataService;

    @Override
    public void productViewCount(ProductDataEntity productDataEntity) {
        ProductData productData = new ProductData();
        PropertiesConvert.copyObjectRepoToApi(productDataEntity, productData);
        productDataService.productViewCount(productData);
    }
}
