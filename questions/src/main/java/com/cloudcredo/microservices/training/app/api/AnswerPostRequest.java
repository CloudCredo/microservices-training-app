package com.cloudcredo.microservices.training.app.api;

class AnswerPostRequest {
  private long questionId;
  private String answer;

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
