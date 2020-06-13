package com.tacbin.town.repo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.town.repo.entity.Product;
import com.tacbin.town.repo.mapper.IProductMapper;
import com.tacbin.town.repo.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-15 11:23
 **/
@Service
@AllArgsConstructor
public class IProductServiceImpl implements IProductService {
    private IProductMapper productMapper;

    @Override
    public List<Product> queryProducts(String categoryId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Product::getCategoryId, categoryId);
        return productMapper.selectList(wrapper);
    }

    @Override
    public void addProduct(Product product) {
        productMapper.insert(product);
    }

    @Override
    public Product queryById(String productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateById(product);
    }

    @Override
    public void deleteProduct(String productId, long userId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Product::getId, productId).eq(Product::getUserID, userId);
        productMapper.delete(wrapper);
    }
}
