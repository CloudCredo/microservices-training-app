package com.cloudcredo.microservices.training.app.context;

import org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.common.RedisServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisShardInfo;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class RedisContext {

    @Profile("cloud")
     static class CloudFoundry {
        @Bean
        public JedisConnectionFactory redisConnectionFactory() throws URISyntaxException {
            CloudFactory cloudFactory = new CloudFactory();
            Cloud cloud = cloudFactory.getCloud();
            RedisServiceInfo serviceInfo = (RedisServiceInfo) cloud.getServiceInfo("cloudcredo-training-redis");

            URI redisUri = new URI(serviceInfo.getUri());
            return new JedisConnectionFactory(new JedisShardInfo(redisUri));
        }
    }

    @Profile("default")
    static class Local {
        @Bean
        public JedisConnectionFactory redisConnectionFactory() {
            return new JedisConnectionFactory();
        }
    }

    @Bean
    public RedisTemplate<String, Integer> redisTemplate(JedisConnectionFactory redJedisConnectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redJedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return redisTemplate;
    }
}
