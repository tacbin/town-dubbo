package com.tacbin.town.repo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.town.common.constants.AppConstants;
import com.tacbin.town.repo.entity.Product;
import com.tacbin.town.repo.mapper.IProductMapper;
import com.tacbin.town.repo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:23
 **/
@Service
public class IProductServiceImpl implements IProductService {
    private IProductMapper productMapper;

    @Override
    public List<Product> queryProducts(String categoryId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Product::getCategoryId, categoryId);
        return getProducts(wrapper);
    }

    @Override
    public void addProduct(Product product) {
        product.setImg1(product.getImg1().replace(AppConstants.IMAGE_HOST, ""));
        product.setImg2(product.getImg2().replace(AppConstants.IMAGE_HOST, ""));
        product.setImg3(product.getImg3().replace(AppConstants.IMAGE_HOST, ""));
        productMapper.insert(product);
    }

    @Override
    public Product queryById(String productId) {
        Product product = productMapper.selectById(productId);
        product.setImg1(AppConstants.IMAGE_HOST + product.getImg1());
        product.setImg2(AppConstants.IMAGE_HOST + product.getImg2());
        product.setImg3(AppConstants.IMAGE_HOST + product.getImg3());
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        product.setImg1(product.getImg1().replace(AppConstants.IMAGE_HOST, ""));
        product.setImg2(product.getImg2().replace(AppConstants.IMAGE_HOST, ""));
        product.setImg3(product.getImg3().replace(AppConstants.IMAGE_HOST, ""));
        productMapper.updateById(product);
    }

    @Override
    public void deleteProduct(String productId, long userId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Product::getId, productId).eq(Product::getUserID, userId);
        productMapper.delete(wrapper);
    }

    @Override
    public List<Product> queryEnableProducts(String categoryId, String userId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Product::getCategoryId, categoryId).eq(Product::getEnable, 1).eq(Product::getUserID, userId);
        return getProducts(wrapper);
    }

    private List<Product> getProducts(QueryWrapper<Product> wrapper) {
        List<Product> products = productMapper.selectList(wrapper);
        for (Product product : products) {
            product.setImg1(AppConstants.IMAGE_HOST + product.getImg1());
            product.setImg2(AppConstants.IMAGE_HOST + product.getImg2());
            product.setImg3(AppConstants.IMAGE_HOST + product.getImg3());
        }
        return products;
    }

    @Autowired
    public void setProductMapper(IProductMapper productMapper) {
        this.productMapper = productMapper;
    }
}
