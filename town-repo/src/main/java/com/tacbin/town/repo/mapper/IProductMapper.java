package com.tacbin.town.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tacbin.town.repo.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProductMapper extends BaseMapper<Product> {

}
