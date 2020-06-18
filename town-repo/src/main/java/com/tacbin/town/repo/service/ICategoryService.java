package com.tacbin.town.repo.service;

import com.tacbin.town.repo.entity.Category;

import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-01 11:57
 **/
public interface ICategoryService {
    List<Category> queryCategory(Long userId);

    Category createCategory(Long userId, String name);

    Category queryCategoryByName(long userId, String name);

    boolean changeName(Long userId, String oldName, String newName);

    boolean deleteCategory(long userId, String name);

    Category queryProductsOfCategory(long userId, String name);

    List<Category> queryCustomerCategory(String userId);
}
