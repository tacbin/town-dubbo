package com.tacbin.town.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tacbin.town.repo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 11:54
 **/
@Mapper
public interface ICategoryMapper extends BaseMapper<Category> {
    Category queryProductsOfCategory(long userId, String name);
}
