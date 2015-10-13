package com.cloudcredo.microservices.training.app.feedback.api;

class FeedbackPostResponse {
  private final String status;

  public FeedbackPostResponse() {
    this.status = "undefined";
  }

  private FeedbackPostResponse(String status) {
    this.status = status;
  }

  public static FeedbackPostResponse ok() {
    return new FeedbackPostResponse("ok");
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "FeedbackPostResponse{" +
      "status='" + status + '\'' +
      '}';
  }
}
