package com.tacbin.town.repo.service;

import com.tacbin.town.repo.entity.Product;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 11:57
 **/
public interface IProductService {
    List<Product> queryProducts(String categoryId);

    void addProduct(Product product);

    Product queryById(String productId);

    void updateProduct(Product product);

    void deleteProduct(String productId, long userId);
}
