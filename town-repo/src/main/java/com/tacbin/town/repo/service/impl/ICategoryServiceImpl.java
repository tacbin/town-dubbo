package com.tacbin.town.repo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tacbin.town.repo.entity.Category;
import com.tacbin.town.repo.mapper.ICategoryMapper;
import com.tacbin.town.repo.service.ICategoryService;
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
public class ICategoryServiceImpl implements ICategoryService {
    private ICategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategory(Long userId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getUserID, userId);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public Category createCategory(Long userId, String name) {
        Category category = new Category();
        category.setName(name);
        category.setUserID(userId);
        categoryMapper.insert(category);
        return categoryMapper.selectById(category.getId());
    }

    @Override
    public Category queryCategoryByName(long userId, String name) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getName, name).eq(Category::getUserID, userId);
        return categoryMapper.selectOne(wrapper);
    }

    @Override
    public boolean changeName(Long userId, String oldName, String newName) {
        Category category = queryCategoryByName(userId, oldName);
        category.setName(newName);
        int result = categoryMapper.updateById(category);
        return result > 0 ? true : false;
    }

    @Override
    public boolean deleteCategory(long userId, String name) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getUserID, userId).eq(Category::getName, name);
        int result = categoryMapper.delete(wrapper);
        return result > 0 ? true : false;
    }

    @Override
    public Category queryProductsOfCategory(long userId, String name) {
        return null;
    }
}
