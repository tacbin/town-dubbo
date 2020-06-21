package com.tacbin.town.api.service;

import com.tacbin.town.api.service.entity.ProductDataEntity;
import com.tacbin.town.api.service.entity.ProductEntity;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-14 15:36
 **/
public interface ProductDataService {
    void productViewCount(ProductDataEntity productDataEntity);

    List<ProductEntity> queryLastTenProducts(long userId);

    List<ProductEntity> queryTodayTenProducts(long id);

    List<ProductDataEntity> queryViewerOfChina(long id);
}
