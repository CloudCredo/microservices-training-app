package com.cloudcredo.microservices.training.app.persistence;

import com.cloudcredo.microservices.training.app.core.AggregatedFeedback;
import com.cloudcredo.microservices.training.app.core.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by cloudcredo on 14/09/2015.
 */
@Service("RedisFeedbackPersistenceService")
public class RedisFeedbackPersistenceService implements FeedbackPersistenceService {

    @Autowired
    private RedisTemplate<Integer, Integer> redisTemplate;

    @Override
    public AggregatedFeedback getAggregatedFeedback() {
        throw new IllegalArgumentException("Not yet implemented");
    }

    @Override
    public void saveFeedback(Feedback feedback) {
        int learningLevel = feedback.getLearningLevel().ordinal();
        int happinessLevel = feedback.getHappinessLevel().ordinal();
        redisTemplate.opsForValue().set(happinessLevel, learningLevel);
    }
}
