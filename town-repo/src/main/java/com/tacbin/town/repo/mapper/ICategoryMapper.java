package com.tacbin.town.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tacbin.town.repo.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 11:54
 **/
@Mapper
public interface ICategoryMapper extends BaseMapper<Category> {
    List<Category> queryProductNumsOfCategory(@Param("userId") String userId);
}
