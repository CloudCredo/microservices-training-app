package com.cloudcredo.microservices.training.app.questions.api;

class AnswerPostResponse {
  private final String status;

  public AnswerPostResponse() {
    this("unknown");
  }

  public AnswerPostResponse(String status) {
    this.status = status;
  }

  public static AnswerPostResponse ok() {
    return new AnswerPostResponse("ok");
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "AnswerPostResponse{" +
      "status='" + status + '\'' +
      '}';
  }
}
