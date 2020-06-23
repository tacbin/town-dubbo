package com.tacbin.town.api.service;

import com.tacbin.town.api.service.entity.CategoryEntity;
import com.tacbin.town.api.service.entity.ProductEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> queryCategory(Long userId);

    CategoryEntity createCategory(long id, String name, int queue);

    CategoryEntity queryCategoryByName(long id, String name);

    boolean changeName(long id, String oldName, String newName, int queue);

    boolean deleteCategory(long userId, String name);

    List<ProductEntity> queryProductsOfCategory(long userId,String name);

    List<CategoryEntity> queryCustomerCategory(String userId);
}
