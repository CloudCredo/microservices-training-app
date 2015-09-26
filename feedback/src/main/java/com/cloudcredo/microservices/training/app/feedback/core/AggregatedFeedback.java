package com.cloudcredo.microservices.training.app.feedback.core;

import java.util.Map;

public class AggregatedFeedback {
  private final Map<HappinessLevel, Integer> happinessLevels;
  private final Map<LearningLevel, Integer> learningLevels;

  public AggregatedFeedback(
    Map<HappinessLevel, Integer> happinessLevels,
    Map<LearningLevel, Integer> learningLevels
  ) {
    this.happinessLevels = happinessLevels;
    this.learningLevels = learningLevels;
  }

  public Map<HappinessLevel, Integer> getHappinessLevels() {
    return happinessLevels;
  }

  public Map<LearningLevel, Integer> getLearningLevels() {
    return learningLevels;
  }
}
