package com.cloudcredo.microservices.training.app.rest;

class FeedbackPostResponse {
  private final String status;

  private FeedbackPostResponse(String status) {
    this.status = status;
  }

  public static FeedbackPostResponse ok() {
    return new FeedbackPostResponse("ok");
  }

  public String getStatus() {
    return status;
  }
}
