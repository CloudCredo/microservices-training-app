package com.cloudcredo.microservices.training.app.persistence

import com.cloudcredo.microservices.training.app.FeedbackApplication
import com.cloudcredo.microservices.training.app.core.Feedback
import com.cloudcredo.microservices.training.app.core.HappinessLevel
import com.cloudcredo.microservices.training.app.core.LearningLevel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Specification

@SpringApplicationConfiguration(classes = FeedbackApplication.class)
class RedisFeedbackPersistenceServiceTest extends Specification {

    @Autowired
    private RedisFeedbackPersistenceService unit;

    @Autowired
    private RedisTemplate<Integer, Integer> redisTemplate

    def setup() {
        redisTemplate.delete(HappinessLevel.HAPPY)
    }

    def "#persists feedback into redis"() {
        given:
        final feedback = new Feedback(HappinessLevel.HAPPY, LearningLevel.LEARNT_A_LOT);

        when:
        unit.saveFeedback(feedback)

        then:
        assert redisTemplate.hasKey(HappinessLevel.HAPPY.ordinal()) == true
    }
}
