package com.cloudcredo.microservices.training.app.questions.api;

class QuestionPostResponse {

  private final String status;

  public QuestionPostResponse() {
    this("unknown");
  }

  public QuestionPostResponse(String status) {
    this.status = status;
  }

  public static QuestionPostResponse ok() {
    return new QuestionPostResponse("ok");
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "QuestionPostResponse{" +
      "status='" + status + '\'' +
      '}';
  }
}
