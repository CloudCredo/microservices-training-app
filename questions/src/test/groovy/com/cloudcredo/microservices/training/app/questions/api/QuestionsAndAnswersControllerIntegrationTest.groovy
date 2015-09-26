package com.cloudcredo.microservices.training.app.questions.api

import com.cloudcredo.microservices.training.app.questions.QuestionsAndAnswersApplication
import com.cloudcredo.microservices.training.app.questions.core.Answer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.http.HttpStatus
import redis.embedded.RedisServer
import spock.lang.Specification

@SpringApplicationConfiguration(classes = QuestionsAndAnswersApplication)
@WebIntegrationTest(randomPort = true)
class QuestionsAndAnswersControllerIntegrationTest extends Specification {

  @Value('${local.server.port}') int port
  @Autowired def RedisConnectionFactory redisConnectionFactory
  def restTemplate = new TestRestTemplate()

  static RedisServer redisServer

  def setupSpec() {
    redisServer = new RedisServer(6379);
    redisServer.start()
  }

  def cleanupSpec() {
    redisServer.stop()
  }

  def setup() {
    redisConnectionFactory.connection.flushAll()
  }

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

  def 'accepts new answers'() {
    given:
    def request = new AnswerPostRequest(answer: 'answer')

    when:
    def response = restTemplate.postForEntity(answersUrl(1), request, AnswerPostResponse)

    then:
    response.statusCode == HttpStatus.OK
  }

  def 'returns known questions'() {
    given:
    def questionsToAsk = [new QuestionPostRequest(question: 'q1'), new QuestionPostRequest(question: 'q2')]
    questionsToAsk.each { request ->
      restTemplate.postForEntity(questionsUrl(), request, QuestionPostResponse)
    }

    when:
    def response = restTemplate.getForEntity(questionsUrl(), QuestionsGetResponse)

    then:
    response.statusCode == HttpStatus.OK
    response.body.questions.collect { it -> it.question } containsAll('q1', 'q2')
  }

  def 'returns known answers to a question'() {
    given:
    def questionsToAsk = [new QuestionPostRequest(question: 'q3'), new QuestionPostRequest(question: 'q4')]
    questionsToAsk.each { request ->
      restTemplate.postForEntity(questionsUrl(), request, QuestionPostResponse)
    }
    def questionsResponse = restTemplate.getForEntity(questionsUrl(), QuestionsGetResponse)
    def questionIds = questionsResponse.body.questions.collect { q -> q.id }
    for (def questionId : questionIds) {
      for (def i = 1; i <=2; i++) {
        def answerRequest = new AnswerPostRequest(questionId: questionId, answer: "Question ${questionId} answer ${i}")
        restTemplate.postForEntity(answersUrl(questionId), answerRequest, AnswerPostResponse)
      }
    }

    when:
    def answers = new HashMap<Long, List<Answer>>()
    def answersResponses = []
    questionIds.each { questionId ->
      def answersResponse = restTemplate.getForEntity(answersUrl(questionId), AnswersGetResponse)
      answersResponse.body.answers.each { answer ->
        answers.putIfAbsent(questionId, [])
        answers.get(questionId).add(answer)
      }
      answersResponses.add(answersResponse)
    }

    then:
    answersResponses.each { response -> response.statusCode == HttpStatus.OK }
    questionIds.each { questionId ->
      answers[questionId].size() == 2
      def answerStrings = answers[questionId].collect { a -> a.answer }
      for (def i = 1; i < 3; i++) {
        answerStrings.contains("Question ${questionId} answer ${i}")
      }
    }
  }
}
