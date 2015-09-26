package com.cloudcredo.microservices.training.app.questions.core

import com.cloudcredo.microservices.training.app.questions.notification.Notifier
import com.cloudcredo.microservices.training.app.questions.persistence.QuestionAndAnswersPersistenceService
import spock.lang.Specification

class QuestionsAndAnswersServiceImplTest extends Specification {

  Notifier notifier = Mock(Notifier)
  QuestionAndAnswersPersistenceService persistenceService = Mock(QuestionAndAnswersPersistenceService)
  QuestionsAndAnswersService questionsAndAnswersService;

  def setup() {
    questionsAndAnswersService = new QuestionsAndAnswersServiceImpl(persistenceService, notifier)
  }

  def "askQuestion"() {
    given:
    Question question = new Question('What is the meaning of life?')

    when:
    questionsAndAnswersService.askQuestion(question)

    then:
    1 * persistenceService.saveQuestion(question)
    1 * notifier.newQuestionAsked(question)
  }

  def "answerQuestion"() {
    given:
    Answer answer = new Answer(1, '42')

    when:
    questionsAndAnswersService.answerQuestion(answer)

    then:
    1 * persistenceService.saveAnswer(answer)
  }

  def "getAllQuestions"() {
    given:
    def expectedQuestions = [
        new Question('foo'),
        new Question('bar')
    ]
    persistenceService.allQuestions >> expectedQuestions

    when:
    def questions = questionsAndAnswersService.allQuestions

    then:
    questions == expectedQuestions
  }
}
