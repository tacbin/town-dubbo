package com.tacbin.town.repo.service;

import com.tacbin.town.repo.entity.Product;
import com.tacbin.town.repo.entity.ProductData;

import java.util.List;

public interface IProductDataService {
    void productViewCount(ProductData productData);

    List<Product> queryLastTenProducts(long userId);

    List<Product> queryTodayTenProducts(long userId);

    List<ProductData> queryViewerOfChina(long userId);
}
