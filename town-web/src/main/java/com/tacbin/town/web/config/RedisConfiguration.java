package com.tacbin.town.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author tacbin
 * Redis配置
 */
@Configuration
public class RedisConfiguration {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port = 6379;
    @Value("${spring.redis.timeout}")
    private int timeout = 1000;
    @Value("${spring.redis.database}")
    private int database = 1;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig conf = new JedisPoolConfig();
        return new JedisPool(conf, host, port, timeout, password, database);
    }
}
