package com.tacbin.town.web.controller.efootball;


import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/customer/efootball")
public class EfootballController {
    @Resource
    private JedisPool jedisPool;

    @RequestMapping(path = "/alive", method = RequestMethod.GET)
    public ResponseInfo<String> isAlive(@RequestParam String uniKey, @RequestParam String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            Thread.sleep(200);
            log.info("redis key：{}", uniKey);
            if (jedis.get(uniKey) == null) {
                jedis.set(uniKey, value);
            }else if (!jedis.get(uniKey).equals(value)){
                return new ResponseInfo<>("", Status.SUCCESS, UUID.randomUUID().toString());
            }
            return new ResponseInfo<>("", Status.SUCCESS, "k" + UUID.randomUUID().toString() + "o");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseInfo<>("", Status.SUCCESS, UUID.randomUUID().toString());
    }
}
