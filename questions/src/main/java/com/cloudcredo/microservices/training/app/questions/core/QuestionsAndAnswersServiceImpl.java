package com.cloudcredo.microservices.training.app.questions.core;

import com.cloudcredo.microservices.training.app.questions.persistence.QuestionAndAnswersPersistenceService;
import com.cloudcredo.microservices.training.app.questions.notification.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class QuestionsAndAnswersServiceImpl implements QuestionsAndAnswersService {

  private final QuestionAndAnswersPersistenceService persistenceService;
  private final Notifier notifier;

  @Autowired
  public QuestionsAndAnswersServiceImpl(QuestionAndAnswersPersistenceService persistenceService, Notifier notifier) {
    this.persistenceService = persistenceService;
    this.notifier = notifier;
  }

  @Override
  public void askQuestion(Question question) {
    persistenceService.saveQuestion(question);
    notifier.newQuestionAsked(question);
  }

  @Override
  public void answerQuestion(Answer answer) {
    persistenceService.saveAnswer(answer);
  }

  @Override
  public List<Question> getAllQuestions() {
    return persistenceService.getAllQuestions();
  }

  @Override
  public List<Answer> getAnswersForQuestion(long questionId) {
    return persistenceService.getAnswersToQuestion(questionId);
  }
}
