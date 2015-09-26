package com.cloudcredo.microservices.training.app.questions.api;

class QuestionPostRequest {
  private String question;

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  @Override
  public String toString() {
    return "QuestionPostRequest{" +
      "question='" + question + '\'' +
      '}';
  }
}
