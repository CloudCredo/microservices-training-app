package com.cloudcredo.microservices.training.app.rest;

class FeedbackPostRequest {
  private int happinessLevel;
  private int learningLevel;

  public int getHappinessLevel() {
    return happinessLevel;
  }

  public void setHappinessLevel(int happinessLevel) {
    this.happinessLevel = happinessLevel;
  }

  public int getLearningLevel() {
    return learningLevel;
  }

  public void setLearningLevel(int learningLevel) {
    this.learningLevel = learningLevel;
  }

  @Override
  public String toString() {
    return "FeedbackPostRequest{" +
      "happinessLevel=" + happinessLevel +
      ", learningLevel=" + learningLevel +
      '}';
  }
}
