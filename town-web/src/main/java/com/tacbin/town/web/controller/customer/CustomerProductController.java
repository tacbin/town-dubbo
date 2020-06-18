package com.tacbin.town.web.controller.customer;

import com.tacbin.town.api.service.ProductDataService;
import com.tacbin.town.api.service.ProductService;
import com.tacbin.town.api.service.entity.ProductDataEntity;
import com.tacbin.town.api.service.entity.ProductEntity;
import com.tacbin.town.common.constants.RedisConstants;
import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.common.utils.PropertiesConvert;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.entity.ProductVO;
import com.tacbin.town.web.util.UserAgentUtils;
import com.tacbin.town.web.util.ip.AddressUtils;
import com.tacbin.town.web.util.ip.IpUtils;
import cz.mallat.uasparser.UserAgentInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-13 23:30
 **/
@RestController
@RequestMapping("/customer/product")
public class CustomerProductController {
    @Reference
    private ProductService productService;

    @Reference
    private ProductDataService productDataService;

    private JedisPool jedisPool;

    @AnalysisLog
    @RequestMapping(path = "/queryProducts", method = RequestMethod.POST)
    public ResponseInfo<List<ProductVO>> queryProducts(HttpServletRequest request, String categoryId, String userId) {
        List<ProductEntity> productEntities = productService.queryEnableProducts(categoryId, userId);
        List<ProductVO> productVOS = new ArrayList<>();
        for (int i = 0; i < productEntities.size(); i++) {
            productVOS.add(new ProductVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(productEntities, productVOS);
        return new ResponseInfo<>("获取用户侧商品数据成功", Status.SUCCESS, productVOS);
    }

    @AnalysisLog
    @RequestMapping(path = "/queryProductById", method = RequestMethod.POST)
    public ResponseInfo<ProductVO> queryProductById(HttpServletRequest request, String productId) {
        recordClient(request, productId);
        ProductEntity productEntity = productService.queryById(productId);
        ProductVO productVO = new ProductVO();
        PropertiesConvert.copyObjectRepoToApi(productEntity, productVO);
        return new ResponseInfo<>(null, Status.SUCCESS, productVO);
    }

    /**
     * 一个ip访问商品10分钟之内不会重复计数
     *
     * @param request
     */
    private void recordClient(HttpServletRequest request, String productId) {
        String ip = IpUtils.getIpAddr(request);
        Jedis jedis = jedisPool.getResource();
        String key = RedisConstants.PAGE_COUNT_PREFIX + ip + ":" + productId;
        if (jedis.get(key) == null) {
            UserAgentInfo userAgentInfo = UserAgentUtils.solveHttpRequest(request);
            String address = AddressUtils.getRealAddressByIP(request);
            SetParams setParams = new SetParams();
            // 10分钟
            setParams.ex(60 * 10).nx();
            jedisPool.getResource().set(key, productId, setParams);
            ProductDataEntity productDataEntity = new ProductDataEntity();
            productDataEntity.setProductId(Long.valueOf(productId));
            productDataEntity.setId(SnowFlakeUtil.generateId());
            productDataEntity.setOsName(userAgentInfo.getOsName());
            productDataEntity.setDevice(userAgentInfo.getDeviceType());
            productDataEntity.setIp(ip);
            productDataEntity.setLocation(address);
            productDataService.productViewCount(productDataEntity);
        }
        jedis.close();
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
