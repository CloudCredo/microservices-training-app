package com.cloudcredo.microservices.training.app.rest;

import com.cloudcredo.microservices.training.app.core.AggregatedFeedback;
import com.cloudcredo.microservices.training.app.core.HappinessLevel;
import com.cloudcredo.microservices.training.app.core.LearningLevel;

import java.util.Map;

class FeedbackGetResponse {
  private final int[] happiness = new int[HappinessLevel.values().length];
  private final int[] learning = new int[LearningLevel.values().length];

  private FeedbackGetResponse() {
  }

  public static FeedbackGetResponse from(AggregatedFeedback feedback) {
    FeedbackGetResponse response = new FeedbackGetResponse();

    extractFeedback(feedback.getHappinessLevels(), response.happiness);
    extractFeedback(feedback.getLearningLevels(), response.learning);

    return response;
  }

  public int[] getHappiness() {
    return happiness;
  }

  public int[] getLearning() {
    return learning;
  }

  private static <T extends Enum<T>> void extractFeedback(Map<T, Integer> source, int[] target) {
    for (Map.Entry<T, Integer> entry : source.entrySet()) {
      target[entry.getKey().ordinal()] = entry.getValue();
    }
  }
}
