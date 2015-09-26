package com.cloudcredo.microservices.training.app.feedback.persistence;

import com.cloudcredo.microservices.training.app.feedback.core.Feedback;
import com.cloudcredo.microservices.training.app.feedback.core.HappinessLevel;
import com.cloudcredo.microservices.training.app.feedback.core.LearningLevel;
import com.cloudcredo.microservices.training.app.feedback.core.AggregatedFeedback;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@Service("InMemoryFeedbackPersistenceService")
class InMemoryFeedbackPersistenceService implements FeedbackPersistenceService {
  private final ConcurrentMap<HappinessLevel, LongAdder> happinessLevels = new ConcurrentHashMap<>();
  private final ConcurrentMap<LearningLevel, LongAdder> learningLevels = new ConcurrentHashMap<>();

  @Override
  public AggregatedFeedback getAggregatedFeedback() {
    return new AggregatedFeedback(
      snapshot(happinessLevels),
      snapshot(learningLevels)
    );
  }

  @Override
  public void saveFeedback(Feedback feedback) {
    incrementHistogram(happinessLevels, feedback.getHappinessLevel());
    incrementHistogram(learningLevels, feedback.getLearningLevel());
  }

  private static <T> void incrementHistogram(Map<T, LongAdder> histogram, T key) {
    histogram.computeIfAbsent(key, k -> new LongAdder()).increment();
  }

  private static <T> Map<T, Integer> snapshot(Map<T, LongAdder> histogram) {
    return histogram.entrySet().stream().collect(
      Collectors.toMap(
        Map.Entry::getKey,
        entry -> (int) entry.getValue().sum()
      )
    );
  }
}
