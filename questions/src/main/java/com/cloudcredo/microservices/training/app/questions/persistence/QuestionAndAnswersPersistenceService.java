package com.cloudcredo.microservices.training.app.questions.persistence;

import com.cloudcredo.microservices.training.app.questions.core.Answer;
import com.cloudcredo.microservices.training.app.questions.core.Question;

import java.util.List;

public interface QuestionAndAnswersPersistenceService {
  Question saveQuestion(Question question);
  List<Question> getAllQuestions();
  void saveAnswer(Answer answer);
  List<Answer> getAnswersToQuestion(long questionId);
}
