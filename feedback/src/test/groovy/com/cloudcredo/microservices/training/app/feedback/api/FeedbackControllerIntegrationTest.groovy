package com.cloudcredo.microservices.training.app.feedback.api

import com.cloudcredo.microservices.training.app.feedback.FeedbackApplication
import com.cloudcredo.microservices.training.app.feedback.api.FeedbackGetResponse
import com.cloudcredo.microservices.training.app.feedback.api.FeedbackPostRequest
import com.cloudcredo.microservices.training.app.feedback.api.FeedbackPostResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import redis.embedded.RedisServer
import spock.lang.Specification

@SpringApplicationConfiguration(classes = FeedbackApplication.class)
@WebIntegrationTest(randomPort = true)
class FeedbackControllerIntegrationTest extends Specification {

  private static RedisServer redisServer

  @Value('${local.server.port}') int port;
  def restTemplate = new TestRestTemplate()

  @Autowired
  private RedisTemplate<String, Integer> redisTemplate


  void setupSpec() {
    redisServer = new RedisServer(6379);
    redisServer.start()
  }

  void cleanupSpec() {
    redisServer.stop()
  }

  def setup() {
    redisTemplate.connectionFactory.connection.flushAll()
  }

  def feedbackUrl() {
    "http://localhost:${port}/feedback"
  }

  def "#can roundtrip feedback"() {
    given:
    FeedbackPostRequest postRequest = new FeedbackPostRequest(happinessLevel: 2, learningLevel: 1)

    when:
    def postResponse = restTemplate.postForEntity(feedbackUrl(), postRequest, FeedbackPostResponse)
    def getResponse = restTemplate.getForEntity(feedbackUrl(), FeedbackGetResponse)

    then:
    postResponse.statusCode == HttpStatus.OK
    postResponse.body.status == 'ok'

    getResponse.statusCode == HttpStatus.OK
    getResponse.getBody().happiness == [0, 0, 1, 0, 0]
    getResponse.getBody().learning == [0, 1, 0]
  }
}
