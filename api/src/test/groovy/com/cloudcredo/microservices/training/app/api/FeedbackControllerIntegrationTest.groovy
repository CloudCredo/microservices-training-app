package com.cloudcredo.microservices.training.app.api

import com.cloudcredo.microservices.training.app.FeedbackApplication
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringApplicationConfiguration(classes = FeedbackApplication.class)
@WebIntegrationTest(randomPort = true)
class FeedbackControllerIntegrationTest extends Specification {

  @Value('${local.server.port}') int port;
  def restTemplate = new TestRestTemplate()

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
