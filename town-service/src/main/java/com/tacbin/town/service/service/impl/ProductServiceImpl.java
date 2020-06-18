package com.tacbin.town.service.service.impl;

import com.tacbin.town.api.service.ProductService;
import com.tacbin.town.api.service.entity.ProductEntity;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.repo.entity.Product;
import com.tacbin.town.repo.service.IProductService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-06 14:48
 **/
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private IProductService iProductService;

    @Override
    public List<ProductEntity> queryEnableProducts(String categoryId, String userId) {
        List<Product> products = iProductService.queryEnableProducts(categoryId,userId);
        return getProductEntities(products);
    }

    private List<ProductEntity> getProductEntities(List<Product> products) {
        if (products.size() == 0) {
            return new ArrayList<>();
        }
        List<ProductEntity> productEntities = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productEntities.add(new ProductEntity());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(products, productEntities);
        return productEntities;
    }

    @Override
    public List<ProductEntity> queryProducts(String categoryId) {
        List<Product> products = iProductService.queryProducts(categoryId);
        return getProductEntities(products);
    }

    @Override
    public void addProduct(ProductEntity productEntity) {
        Product product = new Product();
        productEntity.setId(product.getId());
        PropertiesConvert.copyObjectRepoToApi(productEntity, product);
        iProductService.addProduct(product);
    }

    @Override
    public ProductEntity queryById(String productId) {
        ProductEntity productEntity = new ProductEntity();
        Product product = iProductService.queryById(productId);
        PropertiesConvert.copyObjectRepoToApi(product, productEntity);
        return product == null ? null : productEntity;
    }

    @Override
    public void updateProduct(ProductEntity productEntity) {
        Product product = new Product();
        PropertiesConvert.copyObjectRepoToApi(productEntity, product);
        iProductService.updateProduct(product);
    }

    @Override
    public void deleteProduct(String productId, long userId) {
        iProductService.deleteProduct(productId,userId);
    }
}
