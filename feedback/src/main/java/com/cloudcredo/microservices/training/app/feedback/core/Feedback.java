package com.cloudcredo.microservices.training.app.feedback.core;

public class Feedback {
  private HappinessLevel happinessLevel;
  private LearningLevel learningLevel;

  public Feedback() {
  }

  public Feedback(HappinessLevel happinessLevel, LearningLevel learningLevel) {
      this.happinessLevel = happinessLevel;
      this.learningLevel = learningLevel;
  }

  public HappinessLevel getHappinessLevel() {
      return happinessLevel;
  }

  public void setHappinessLevel(HappinessLevel happinessLevel) {
      this.happinessLevel = happinessLevel;
  }

  public LearningLevel getLearningLevel() {
    return learningLevel;
  }

  public void setLearningLevel(LearningLevel learningLevel) {
    this.learningLevel = learningLevel;
  }

  @Override
  public String toString() {
    return "Feedback{" +
      "happinessLevel=" + happinessLevel +
      ", learningLevel=" + learningLevel +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Feedback feedback = (Feedback) o;

    if (happinessLevel != feedback.happinessLevel) return false;
    return learningLevel == feedback.learningLevel;

  }

  @Override
  public int hashCode() {
    int result = happinessLevel != null ? happinessLevel.hashCode() : 0;
    result = 31 * result + (learningLevel != null ? learningLevel.hashCode() : 0);
    return result;
  }
}
