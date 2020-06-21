package com.tacbin.town.web.controller.customer;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-13 23:30
 **/
@RestController
@Slf4j
@RequestMapping("/customer/product")
public class CustomerProductController {
    @Reference
    private ProductService productService;

    @Reference
    private ProductDataService productDataService;

    private JedisPool jedisPool;

    // 客户端商品缓存，10秒过期
    private Cache<String, String> productCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    @AnalysisLog
    @RequestMapping(path = "/queryProducts", method = RequestMethod.POST)
    public ResponseInfo<List<ProductVO>> queryProducts(HttpServletRequest request, String categoryId, String userId) {
        String key = categoryId + userId;
        String value = productCache.getIfPresent(key);
        if (!StringUtils.isEmpty(value)) {
            return new ResponseInfo<>("获取用户侧商品数据成功", Status.SUCCESS, JSON.parseArray(value, ProductVO.class));
        }
        List<ProductEntity> productEntities = productService.queryEnableProducts(categoryId, userId);
        List<ProductVO> productVOS = new ArrayList<>();
        for (int i = 0; i < productEntities.size(); i++) {
            productVOS.add(new ProductVO());
        }
        PropertiesConvert.copyListObjectOfRepoToApi(productEntities, productVOS);
        productCache.put(key, JSON.toJSONString(productVOS));
        return new ResponseInfo<>("获取用户侧商品数据成功", Status.SUCCESS, productVOS);
    }

    @AnalysisLog
    @RequestMapping(path = "/queryProductById", method = RequestMethod.POST)
    public ResponseInfo<ProductVO> queryProductById(HttpServletRequest request, String productId) {
        String key = productId;
        String value = productCache.getIfPresent(key);
        if (!StringUtils.isEmpty(value)) {
            return new ResponseInfo<>("获取用户侧商品数据成功", Status.SUCCESS, JSON.parseObject(value, ProductVO.class));
        }
        recordClient(request, productId);
        ProductEntity productEntity = productService.queryById(productId);
        ProductVO productVO = new ProductVO();
        PropertiesConvert.copyObjectRepoToApi(productEntity, productVO);
        productCache.put(key, JSON.toJSONString(productVO));
        return new ResponseInfo<>(null, Status.SUCCESS, productVO);
    }

    /**
     * 一个ip访问商品10分钟之内不会重复计数
     *
     * @param request
     */
    private void recordClient(HttpServletRequest request, String productId) {
        String ip = IpUtils.getIpAddr(request);
        try (Jedis jedis = jedisPool.getResource()) {
            String key = RedisConstants.PAGE_COUNT_PREFIX + ip + ":" + productId;
            log.info("redis key：{}", key);
            if (jedis.get(key) == null) {
                String address = AddressUtils.getRealAddressByIP(request);
                UserAgentInfo userAgentInfo = UserAgentUtils.solveHttpRequest(request);
                SetParams setParams = new SetParams();
                // 10分钟
                setParams.ex(60 * 10).nx();
                jedis.set(key, productId, setParams);
                ProductDataEntity productDataEntity = new ProductDataEntity();
                productDataEntity.setProductId(Long.valueOf(productId));
                productDataEntity.setId(SnowFlakeUtil.generateId());
                productDataEntity.setOsName(userAgentInfo.getOsName());
                productDataEntity.setDevice(userAgentInfo.getDeviceType());
                productDataEntity.setIp(ip);
                productDataEntity.setLocation(address);
                productDataService.productViewCount(productDataEntity);
            }
        }
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
