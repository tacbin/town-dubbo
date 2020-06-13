package com.tacbin.town.web.controller;

import com.tacbin.town.api.service.ProductService;
import com.tacbin.town.api.service.entity.ProductEntity;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.entity.ProductVO;
import com.tacbin.town.web.util.UserInfoBeanUtil;
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
 * @Date : 2020-06-06 14:45
 **/
@RestController
@RequestMapping("/product")
public class ProductController {
    @Reference
    private ProductService productService;

    private UserInfoBeanUtil userInfoBeanUtil;

    @AnalysisLog
    @RequestMapping(path = "/queryProducts", method = RequestMethod.POST)
    public ResponseInfo<List<ProductVO>> queryProducts(String categoryId) {
        List<ProductEntity> productEntities = productService.queryProducts(categoryId);
        List<ProductVO> productVOS = new ArrayList<>();
        for (int i = 0; i < productEntities.size(); i++) {
            productVOS.add(new ProductVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(productEntities, productVOS);
        return new ResponseInfo<>("", Status.SUCCESS, productVOS);
    }

    @AnalysisLog
    @RequestMapping(path = "/addProduct", method = RequestMethod.POST)
    public ResponseInfo<Void> addProduct(ProductVO productVO) {
        productVO.setUserID(userInfoBeanUtil.getCurrentUser().getId());
        ProductEntity entity = new ProductEntity();
        PropertiesConvert.copyObjectRepoToApi(productVO, entity);
        productService.addProduct(entity);
        return new ResponseInfo<>(null, Status.SUCCESS, null);
    }

    @AnalysisLog
    @RequestMapping(path = "/queryProductById", method = RequestMethod.POST)
    public ResponseInfo<ProductVO> queryById(String productId) {
        ProductEntity productEntity = productService.queryById(productId);
        ProductVO productVO = new ProductVO();
        PropertiesConvert.copyObjectRepoToApi(productEntity, productVO);
        return new ResponseInfo<>(null, Status.SUCCESS, productVO);
    }

    @AnalysisLog
    @RequestMapping(path = "/updateProduct", method = RequestMethod.POST)
    public void updateProduct(ProductVO productVo) {
        ProductEntity productEntity = new ProductEntity();
        productVo.setUserID(userInfoBeanUtil.getCurrentUser().getId());
        PropertiesConvert.copyObjectRepoToApi(productVo, productEntity);
        productService.updateProduct(productEntity);
    }

    @AnalysisLog
    @RequestMapping(path = "/deleteProduct", method = RequestMethod.POST)
    public void deleteProduct(String productId){
        productService.deleteProduct(productId,userInfoBeanUtil.getCurrentUser().getId());
    }

    @Autowired
    public void setUserInfoBeanUtil(UserInfoBeanUtil userInfoBeanUtil) {
        this.userInfoBeanUtil = userInfoBeanUtil;
    }
}
