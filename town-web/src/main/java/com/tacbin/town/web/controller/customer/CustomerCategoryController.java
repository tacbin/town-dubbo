package com.tacbin.town.web.controller.customer;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tacbin.town.api.service.CategoryService;
import com.tacbin.town.api.service.ProductService;
import com.tacbin.town.api.service.entity.CategoryEntity;
import com.tacbin.town.api.service.entity.UserInfo;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.entity.CategoryVO;
import com.tacbin.town.web.entity.ProductVO;
import com.tacbin.town.web.util.UserInfoBeanUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-13 18:52
 **/
@RestController
@RequestMapping("/customer/category")
public class CustomerCategoryController {
    @Reference
    private CategoryService categoryService;

    @Reference
    private ProductService productService;

    private UserInfoBeanUtil userInfoBeanUtil;

    // 客户端商品缓存，10秒过期
    private Cache<String, String> categoryCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    @AnalysisLog
    @RequestMapping(path = "/getAll", method = RequestMethod.POST)
    public ResponseInfo<List<CategoryVO>> queryCategory(String userId) {
        String key = userId;
        String value = categoryCache.getIfPresent(key);
        if (!StringUtils.isEmpty(value)) {
            return new ResponseInfo<>("获取用户侧商品数据成功", Status.SUCCESS, JSON.parseArray(value, CategoryVO.class));
        }
        List<CategoryEntity> categoryEntities = categoryService.queryCustomerCategory(userId);
        List<CategoryVO> customerCategoryVOS = new ArrayList<>();
        for (int i = 0; i < categoryEntities.size(); i++) {
            protectCategoryData(categoryEntities.get(i));
            customerCategoryVOS.add(new CategoryVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(categoryEntities, customerCategoryVOS);
        categoryCache.put(key, JSON.toJSONString(categoryEntities));
        return new ResponseInfo<>("获取用户侧目录数据成功", Status.SUCCESS, customerCategoryVOS);
    }

    private void protectCategoryData(CategoryEntity categoryEntity) {
        categoryEntity.setCreateTime(null);
        categoryEntity.setModifyTime(null);
    }
}
