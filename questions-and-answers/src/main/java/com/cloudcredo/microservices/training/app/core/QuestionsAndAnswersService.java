package com.cloudcredo.microservices.training.app.core;

import java.util.List;

public interface QuestionsAndAnswersService {
  void askQuestion(Question question);
  void answerQuestion(Answer answer);
  List<Question> getAllQuestions();
}
