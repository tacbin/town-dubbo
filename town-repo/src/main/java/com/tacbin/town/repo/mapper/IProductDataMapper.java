package com.tacbin.town.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tacbin.town.repo.entity.Product;
import com.tacbin.town.repo.entity.ProductData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface IProductDataMapper extends BaseMapper<ProductData> {
    List<Product> queryLastDaysProducts(@Param("userId") long userId,@Param("daysAgo") String daysAgo);

    List<ProductData> countViewerOfCountry(@Param("userId") long userId);
}
