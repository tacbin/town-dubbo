package com.tacbin.town.service.service.impl;

import com.tacbin.town.api.service.CategoryService;
import com.tacbin.town.api.service.entity.CategoryEntity;
import com.tacbin.town.api.service.entity.ProductEntity;
import com.tacbin.town.repo.entity.Category;
import com.tacbin.town.repo.service.ICategoryService;
import com.tacbin.town.common.utils.PropertiesConvert;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-06 12:36
 **/
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private ICategoryService iCategoryService;

    @Override
    public List<CategoryEntity> queryCategory(Long userId) {
        List<Category> categories = iCategoryService.queryCategory(userId);
        if (categories == null) {
            return new ArrayList<>();
        }
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            categoryEntities.add(new CategoryEntity());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(categories, categoryEntities);
        return categoryEntities;
    }

    @Override
    public CategoryEntity createCategory(long id, String name) {
        Category category = iCategoryService.createCategory(id, name);
        CategoryEntity categoryEntity = new CategoryEntity();
        PropertiesConvert.copyObjectRepoToApi(category, categoryEntity);
        return category == null ? null : categoryEntity;
    }

    @Override
    public CategoryEntity queryCategoryByName(long userId, String name) {
        Category category = iCategoryService.queryCategoryByName(userId, name);
        CategoryEntity categoryEntity = new CategoryEntity();
        PropertiesConvert.copyObjectRepoToApi(category, categoryEntity);
        return category == null ? null : categoryEntity;
    }

    @Override
    public boolean changeName(long userId, String oldName, String newName) {
        return iCategoryService.changeName(userId,oldName,newName);
    }

    @Override
    public boolean deleteCategory(long userId, String name) {
       return iCategoryService.deleteCategory(userId,name);
    }

    @Override
    public List<ProductEntity> queryProductsOfCategory(long userId, String name) {
        iCategoryService.queryProductsOfCategory(userId,name);
        return null;
    }
}
