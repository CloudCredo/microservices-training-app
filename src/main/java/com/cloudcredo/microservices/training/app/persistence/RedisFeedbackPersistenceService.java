package com.cloudcredo.microservices.training.app.persistence;

import com.cloudcredo.microservices.training.app.core.AggregatedFeedback;
import com.cloudcredo.microservices.training.app.core.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * Created by cloudcredo on 14/09/2015.
 */
@Service("RedisFeedbackPersistenceService")
class RedisFeedbackPersistenceService implements FeedbackPersistenceService {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public AggregatedFeedback getAggregatedFeedback() {
        throw new IllegalArgumentException("Not yet implemented");
    }

    @Override
    public void saveFeedback(Feedback feedback) {
        incrementHappinessLevel(feedback);
        incrementLearningLevel(feedback);
    }

    private void incrementHappinessLevel(Feedback feedback) {
        String learningLevel = feedback.getHappinessLevel().name();
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.setIfAbsent(learningLevel, 0);
        operations.increment(learningLevel, 1);
    }

    private void incrementLearningLevel(Feedback feedback) {
        String learningLevel = feedback.getLearningLevel().name();
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.setIfAbsent(learningLevel, 0);
        operations.increment(learningLevel, 1);
    }
}
