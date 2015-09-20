package com.cloudcredo.microservices.training.app.persistence;

import com.cloudcredo.microservices.training.app.core.Answer;
import com.cloudcredo.microservices.training.app.core.Question;

import java.util.List;

public interface QuestionAndAnswersPersistenceService {
  Question saveQuestion(Question question);
  List<Question> getAllQuestions();
  void saveAnswer(Answer answer);
  List<Answer> getAnswersToQuestion(Question question);
}
