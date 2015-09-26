package com.cloudcredo.microservices.training.app.questions.api

import com.cloudcredo.microservices.training.app.questions.core.Question
import com.cloudcredo.microservices.training.app.questions.core.Answer
import com.cloudcredo.microservices.training.app.questions.core.QuestionsAndAnswersService
import spock.lang.Specification

class QuestionsAndAnswersControllerTest extends Specification {

  def service = Mock(QuestionsAndAnswersService)
  def controller = new QuestionsAndAnswersController(service)

  def "AskQuestion"() {
    given:
    def questionText = 'what is the meaning of life?'
    def request = new QuestionPostRequest(question: questionText)
    def expectedQuestion = new Question(questionText)

    when:
    controller.askQuestion(request)

    then:
    1 * service.askQuestion(expectedQuestion)
  }

  def "GetQuestions"() {
    given:
    def questions = [new Question('foo'), new Question('bar')]

    when:
    def response = controller.getQuestions()

    then:
    1 * service.allQuestions >> questions
    response.questions == questions
  }

  def "AnswerQuestion"() {
    given:
    def questionId = 1
    def answerText = '42'
    def request = new AnswerPostRequest(answer: answerText)
    def expectedAnswer = new Answer(questionId, answerText)

    when:
    controller.answerQuestion(questionId, request)

    then:
    1 * service.answerQuestion(expectedAnswer)
  }
}
