package com.cloudcredo.microservices.training.app.feedback.context;

import com.cloudcredo.microservices.training.app.feedback.requestrecorder.RequestMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class RedisContext {
  @Bean
  public RedisConnectionFactory feedbackRedisConnectionFactory(@Value("${vcap.services.feedback-redis.credentials.url}") String url) throws URISyntaxException {
    return redisConnectionFactory(url);
  }

  @Bean
  public RedisTemplate<String, Integer> stringToIntegerRedisTemplate(@Qualifier("feedbackRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Configuration
  static class AsyncRedisContext {
    @Value("${vcap.services.async-redis.credentials.url}") String asyncRedisUrl;

    @Bean
    public RedisTemplate<String, RequestMetadata> stringToRequestMetadataRedisTemplate() throws URISyntaxException {
      RedisConnectionFactory connectionFactory = redisConnectionFactory(asyncRedisUrl);

      RedisTemplate<String, RequestMetadata> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(connectionFactory);
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RequestMetadata.class));
      redisTemplate.afterPropertiesSet();
      return redisTemplate;
    }
  }

  private static RedisConnectionFactory redisConnectionFactory(String url) throws URISyntaxException {
    URI redisUrl = new URI(url);
    JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
    redisConnectionFactory.setUsePool(true);
    redisConnectionFactory.setHostName(redisUrl.getHost());
    redisConnectionFactory.setPort(redisUrl.getPort() >= 0 ? redisUrl.getPort() : 6379);
    if (redisUrl.getUserInfo() != null) {
      String[] userInfo = redisUrl.getUserInfo().split(":");
      redisConnectionFactory.setPassword(userInfo[1]);
    }
    redisConnectionFactory.afterPropertiesSet();
    return redisConnectionFactory;
  }
}
