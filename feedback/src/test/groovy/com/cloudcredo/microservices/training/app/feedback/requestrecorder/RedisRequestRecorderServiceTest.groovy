package com.cloudcredo.microservices.training.app.feedback.requestrecorder

import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisOperations
import spock.lang.Specification

class RedisRequestRecorderServiceTest extends Specification {

  def redisTemplate = Mock(RedisOperations)
  def requestRecorderService = new RedisRequestRecorderService(redisTemplate)

  def "RecordAPICall"() {
    given:
    def path = '/a/path'
    def method = 'POST'
    def expectedRequestMetadata = new RequestMetadata(method, path);
    def opsForList = Mock(ListOperations)
    _ * redisTemplate.opsForList() >> opsForList

    when:
    requestRecorderService.recordAPICall(path, method)

    then:
    1 * opsForList.leftPush('requestMetadata', expectedRequestMetadata)
  }
}
