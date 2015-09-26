package com.cloudcredo.microservices.training.app.questions.persistence;

import com.cloudcredo.microservices.training.app.questions.core.Answer;
import com.cloudcredo.microservices.training.app.questions.core.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
class PersistenceContext {

  @Autowired RedisConnectionFactory redisConnectionFactory;

  @Bean
  public RedisTemplate<String, Question> questionRedisTemplate() {
    return templateFactory(Question.class);
  }

  @Bean
  public RedisTemplate<String, Answer> answerRedisTemplate() {
    return templateFactory(Answer.class);
  }

  private <T> RedisTemplate<String, T> templateFactory(Class<T> clazz) {
    RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(clazz));
    return redisTemplate;
  }
}
