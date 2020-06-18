package com.tacbin.town.api.service;

import com.tacbin.town.api.service.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> queryEnableProducts(String categoryId,String userId);

    List<ProductEntity> queryProducts(String categoryId);

    void addProduct(ProductEntity productEntity);

    ProductEntity queryById(String productId);

    void updateProduct(ProductEntity productEntity);

    void deleteProduct(String productId, long userId);
}
