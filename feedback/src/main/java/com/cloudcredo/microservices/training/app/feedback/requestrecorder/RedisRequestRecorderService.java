package com.cloudcredo.microservices.training.app.feedback.requestrecorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

@Service
class RedisRequestRecorderService implements RequestRecorderService {

  private final RedisOperations<String, RequestMetadata> redisTemplate;

  @Autowired
  public RedisRequestRecorderService(RedisOperations<String, RequestMetadata> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void recordAPICall(String path, String method) {
    RequestMetadata metadata = new RequestMetadata(method, path);
    redisTemplate.opsForList().leftPush("requestMetadata", metadata);
  }
}
