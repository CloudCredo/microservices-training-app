package com.cloudcredo.microservices.training.app.feedback.persistence;

import com.cloudcredo.microservices.training.app.feedback.core.AggregatedFeedback;
import com.cloudcredo.microservices.training.app.feedback.core.Feedback;
import com.cloudcredo.microservices.training.app.feedback.core.HappinessLevel;
import com.cloudcredo.microservices.training.app.feedback.core.LearningLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("RedisFeedbackPersistenceService")
class RedisFeedbackPersistenceService implements FeedbackPersistenceService {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public AggregatedFeedback getAggregatedFeedback() {
      ValueOperations<String, Integer> operations = redisTemplate.opsForValue();

      Function<Enum<?>, Integer> levelToCount = level -> {
        Integer count = operations.get(level.name());
        return count != null ? count : 0;
      };

      Map<HappinessLevel, Integer> aggregatedHappiness = Arrays.stream(HappinessLevel.values())
          .collect(Collectors.toMap(Function.identity(), levelToCount));
      Map<LearningLevel, Integer> aggregatedLearning = Arrays.stream(LearningLevel.values())
          .collect(Collectors.toMap(Function.identity(), levelToCount));

      return new AggregatedFeedback(aggregatedHappiness, aggregatedLearning);
    }

    @Override
    public void saveFeedback(Feedback feedback) {
        incrementHappinessLevel(feedback);
        incrementLearningLevel(feedback);
    }

    private void incrementHappinessLevel(Feedback feedback) {
        String happinessLevel = feedback.getHappinessLevel().name();
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.setIfAbsent(happinessLevel, 0);
        operations.increment(happinessLevel, 1);
    }

    private void incrementLearningLevel(Feedback feedback) {
        String learningLevel = feedback.getLearningLevel().name();
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.setIfAbsent(learningLevel, 0);
        operations.increment(learningLevel, 1);
    }
}
