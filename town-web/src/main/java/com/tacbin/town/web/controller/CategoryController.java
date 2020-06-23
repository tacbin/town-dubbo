package com.tacbin.town.web.controller;

import com.tacbin.town.api.service.CategoryService;
import com.tacbin.town.api.service.ProductService;
import com.tacbin.town.api.service.entity.CategoryEntity;
import com.tacbin.town.api.service.entity.ProductEntity;
import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.entity.CategoryVO;
import com.tacbin.town.web.util.UserInfoBeanUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-06 12:25
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Reference
    private CategoryService categoryService;

    @Reference
    private ProductService productService;

    private UserInfoBeanUtil userInfoBeanUtil;

    @AnalysisLog
    @RequestMapping(path = "/getAll", method = RequestMethod.POST)
    public ResponseInfo<List<CategoryVO>> queryCategory() {
        UserInfo user = userInfoBeanUtil.getCurrentUser();
        List<CategoryEntity> categoryEntities = categoryService.queryCategory(user.getId());
        List<CategoryVO> categoryVOS = new ArrayList<>();
        for (int i = 0; i < categoryEntities.size(); i++) {
            categoryVOS.add(new CategoryVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(categoryEntities, categoryVOS);
        return new ResponseInfo<>("", Status.SUCCESS, categoryVOS);
    }

    @AnalysisLog
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseInfo<CategoryVO> createCategory(String name, int queue) {
        UserInfo user = userInfoBeanUtil.getCurrentUser();
        CategoryEntity entity = categoryService.queryCategoryByName(user.getId(), name);
        if (entity != null) {
            return new ResponseInfo<>("已经存在同名的目录!", Status.FAIL, null);
        }
        CategoryEntity categoryEntity = categoryService.createCategory(user.getId(), name, queue);
        CategoryVO categoryVO = new CategoryVO();
        PropertiesConvert.copyObjectRepoToApi(categoryEntity, categoryVO);
        return new ResponseInfo<>("", Status.SUCCESS, categoryVO);
    }

    @AnalysisLog
    @RequestMapping(path = "/changeName", method = RequestMethod.POST)
    public ResponseInfo<Boolean> changeName(String oldName, @RequestParam(required = false) String newName, @RequestParam(required = false) int queue) {
        UserInfo user = userInfoBeanUtil.getCurrentUser();
        CategoryEntity entity = categoryService.queryCategoryByName(user.getId(), newName);
        if (entity != null) {
            return new ResponseInfo<>("新目录名已经存在!", Status.FAIL, null);
        }
        boolean isSuccess = categoryService.changeName(user.getId(), oldName, newName,queue);
        if (isSuccess) {
            return new ResponseInfo<>("", Status.SUCCESS, null);
        } else {
            return new ResponseInfo<>("替换失败", Status.FAIL, null);
        }
    }

    @AnalysisLog
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public ResponseInfo<Boolean> deleteCategory(String name) {
        UserInfo user = userInfoBeanUtil.getCurrentUser();
        CategoryEntity entity = categoryService.queryCategoryByName(user.getId(), name);
        List<ProductEntity> products = productService.queryProducts(Long.toString(entity.getId()));
        if (products.size() > 0) {
            return new ResponseInfo<>("删除失败，该目录下有商品。", Status.FAIL, null);
        }
        categoryService.deleteCategory(user.getId(), name);
        return new ResponseInfo<>("", Status.SUCCESS, null);
    }

    @Autowired
    public void setUserInfoBeanUtil(UserInfoBeanUtil userInfoBeanUtil) {
        this.userInfoBeanUtil = userInfoBeanUtil;
    }
}
