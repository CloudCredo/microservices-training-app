package com.cloudcredo.microservices.training.app.api;

import com.cloudcredo.microservices.training.app.core.Question;

import java.util.Collections;
import java.util.List;

class QuestionsGetResponse {

  private final List<Question> questions;

  public QuestionsGetResponse() {
    this(Collections.emptyList());
  }

  private QuestionsGetResponse(List<Question> questions) {
    this.questions = questions;
  }

  public static QuestionsGetResponse from(List<Question> questions) {
    return new QuestionsGetResponse(questions);
  }

  public List<Question> getQuestions() {
    return questions;
  }
}
