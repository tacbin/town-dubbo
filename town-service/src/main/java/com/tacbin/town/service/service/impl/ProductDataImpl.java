package com.tacbin.town.service.service.impl;

import com.tacbin.town.api.service.ProductDataService;
import com.tacbin.town.api.service.entity.CategoryEntity;
import com.tacbin.town.api.service.entity.ProductDataEntity;
import com.tacbin.town.api.service.entity.ProductEntity;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.repo.entity.Product;
import com.tacbin.town.repo.entity.ProductData;
import com.tacbin.town.repo.mapper.IProductDataMapper;
import com.tacbin.town.repo.service.IProductDataService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<ProductEntity> queryLastTenProducts(long userId) {
        List<Product> products = productDataService.queryLastTenProducts(userId);
        return getProductEntities(products);
    }

    @Override
    public List<ProductEntity> queryTodayTenProducts(long userId) {
        List<Product> products = productDataService.queryTodayTenProducts(userId);
        return getProductEntities(products);
    }

    @Override
    public List<ProductDataEntity> queryViewerOfChina(long userId) {
        List<ProductData> productDataList = productDataService.queryViewerOfChina(userId);
        if (productDataList == null) {
            return new ArrayList<>();
        }
        List<ProductDataEntity> productEntities = new ArrayList<>();
        for (int i = 0; i < productDataList.size(); i++) {
            productEntities.add(new ProductDataEntity());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(productDataList, productEntities);
        return productEntities;
    }

    private List<ProductEntity> getProductEntities(List<Product> products) {
        if (products == null) {
            return new ArrayList<>();
        }
        List<ProductEntity> productEntities = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productEntities.add(new ProductEntity());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(products, productEntities);
        return productEntities;
    }

}
