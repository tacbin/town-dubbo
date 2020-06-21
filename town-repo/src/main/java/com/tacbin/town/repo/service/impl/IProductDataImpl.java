package com.tacbin.town.repo.service.impl;

import com.tacbin.town.repo.entity.Product;
import com.tacbin.town.repo.entity.ProductData;
import com.tacbin.town.repo.mapper.IProductDataMapper;
import com.tacbin.town.repo.service.IProductDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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

    @Override
    public List<Product> queryLastTenProducts(long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, -30);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tenDaysAgo = sdf.format(calendar.getTime());
        return productDataMapper.queryLastDaysProducts(userId, tenDaysAgo);
    }

    @Override
    public List<Product> queryTodayTenProducts(long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(calendar.getTime());
        return productDataMapper.queryLastDaysProducts(userId, today);
    }

    @Override
    public List<ProductData> queryViewerOfChina(long userId) {
        return productDataMapper.countViewerOfCountry(userId);
    }
}
