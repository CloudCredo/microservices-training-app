package com.cloudcredo.microservices.training.app.api
import com.cloudcredo.microservices.training.app.QuestionsAndAnswersApplication
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringApplicationConfiguration(classes = QuestionsAndAnswersApplication)
@WebIntegrationTest(randomPort = true)
class QuestionsAndAnswersControllerIntegrationTest extends Specification {

  @Value('${local.server.port}') int port
  def restTemplate = new TestRestTemplate()

  def baseUrl() {
    "http://localhost:${port}"
  }

  def questionsUrl() {
    "${baseUrl()}/questions"
  }

  def answersUrl(questionId) {
    "${questionsUrl()}/${questionId}/answers"
  }

  def 'accepts new questions'() {
    given:
    def request = new QuestionPostRequest(question: 'question')

    when:
    def response = restTemplate.postForEntity(questionsUrl(), request, QuestionPostResponse)

    then:
    response.statusCode == HttpStatus.OK
  }

  def 'returns known questions'() {
    given:
    def questionsToAsk = [new QuestionPostRequest(question: 'foo'), new QuestionPostRequest(question: 'bar')]
    questionsToAsk.each { request ->
      restTemplate.postForEntity(questionsUrl(), request, QuestionPostResponse)
    }

    when:
    def response = restTemplate.getForEntity(questionsUrl(), QuestionsGetResponse)

    then:
    response.statusCode == HttpStatus.OK
    response.body.questions.collect { it -> it.question } containsAll('foo', 'bar')
  }

  def 'accepts new answers'() {
    given:
    def request = new AnswerPostRequest(answer: 'answer')

    when:
    def response = restTemplate.postForEntity(answersUrl(1), request, AnswerPostResponse)

    then:
    response.statusCode == HttpStatus.OK
  }

  // TODO answers GET response
//  def 'returns known answers'() {
//    given:
//    def questionId = 1
//    def answersToStore = [
//        new AnswerPostRequest('Answer A'),
//        new AnswerPostRequest('Answer B')
//    ]
//    answersToStore.each { answer ->
//      restTemplate.postForEntity(answersUrl(questionId), answer, AnswerPostResponse)
//    }
//
//    when:
//    restTemplate.getForEntity(answersUrl(questionId), Answer)
//  }

}
