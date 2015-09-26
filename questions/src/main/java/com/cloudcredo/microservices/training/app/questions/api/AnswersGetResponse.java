package com.cloudcredo.microservices.training.app.questions.api;

import com.cloudcredo.microservices.training.app.questions.core.Answer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class AnswersGetResponse {
  private final List<Answer> answers;

  public AnswersGetResponse(@JsonProperty("answers") List<Answer> answers) {
    this.answers = answers;
  }

  public static AnswersGetResponse from(List<Answer> answers) {
    return new AnswersGetResponse(answers);
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  @Override
  public String toString() {
    return "AnswersGetResponse{" +
        "answers=" + answers +
        '}';
  }
}
