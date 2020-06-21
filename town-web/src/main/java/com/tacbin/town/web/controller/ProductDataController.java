package com.tacbin.town.web.controller;

import com.tacbin.town.api.service.ProductDataService;
import com.tacbin.town.api.service.entity.ProductDataEntity;
import com.tacbin.town.api.service.entity.ProductEntity;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.entity.ProductDataVO;
import com.tacbin.town.web.entity.ProductVO;
import com.tacbin.town.web.util.UserInfoBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-21 12:34
 **/
@RestController
@RequestMapping("/productData")
@Slf4j
public class ProductDataController {
    @Reference
    private ProductDataService productDataService;
    private UserInfoBeanUtil userInfoBeanUtil;

    @AnalysisLog
    @RequestMapping(path = "/lastTenProducts", method = RequestMethod.POST)
    public ResponseInfo<List<ProductVO>> queryLastTenProducts() {
        List<ProductEntity> productEntities = productDataService.queryLastTenProducts(userInfoBeanUtil.getCurrentUser().getId());
        List<ProductVO> productVOS = new ArrayList<>();
        for (int i = 0; i < productEntities.size(); i++) {
            productVOS.add(new ProductVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(productEntities, productVOS);
        return new ResponseInfo<>("数据查询成功", Status.SUCCESS, productVOS);
    }

    @AnalysisLog
    @RequestMapping(path = "/todayTenProducts", method = RequestMethod.POST)
    public ResponseInfo<List<ProductVO>> queryTodayTenProducts() {
        List<ProductEntity> productEntities = productDataService.queryTodayTenProducts(userInfoBeanUtil.getCurrentUser().getId());
        List<ProductVO> productVOS = new ArrayList<>();
        for (int i = 0; i < productEntities.size(); i++) {
            productVOS.add(new ProductVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(productEntities, productVOS);
        return new ResponseInfo<>("今日份数据查询成功", Status.SUCCESS, productVOS);
    }

    @AnalysisLog
    @RequestMapping(path = "/viewerOfChina", method = RequestMethod.POST)
    public ResponseInfo<List<ProductDataVO>> queryViewerOfChina() {
        List<ProductDataEntity> productEntities = productDataService.queryViewerOfChina(userInfoBeanUtil.getCurrentUser().getId());
        List<ProductDataVO> productDataVOS = new ArrayList<>();
        for (int i = 0; i < productEntities.size(); i++) {
            productDataVOS.add(new ProductDataVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(productEntities, productDataVOS);
        return new ResponseInfo<>("数据查询成功", Status.SUCCESS, productDataVOS);
    }

    @Autowired
    public void setUserInfoBeanUtil(UserInfoBeanUtil userInfoBeanUtil) {
        this.userInfoBeanUtil = userInfoBeanUtil;
    }
}
