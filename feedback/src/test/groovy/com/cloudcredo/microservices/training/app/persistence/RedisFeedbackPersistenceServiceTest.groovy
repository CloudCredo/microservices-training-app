package com.cloudcredo.microservices.training.app.persistence

import com.cloudcredo.microservices.training.app.FeedbackApplication
import com.cloudcredo.microservices.training.app.core.Feedback
import com.cloudcredo.microservices.training.app.core.HappinessLevel
import com.cloudcredo.microservices.training.app.core.LearningLevel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.data.redis.core.RedisTemplate
import redis.embedded.RedisServer
import spock.lang.Specification

@SpringApplicationConfiguration(classes = FeedbackApplication.class)
class RedisFeedbackPersistenceServiceTest extends Specification {

    @Autowired
    private RedisFeedbackPersistenceService unit

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate

    private static RedisServer redisServer

    void setupSpec() {
        redisServer = new RedisServer(6379);
        redisServer.start()
    }

    void cleanupSpec() {
        redisServer.stop()
    }

    def setup() {
        redisTemplate.delete(HappinessLevel.HAPPY.name())
        redisTemplate.delete(LearningLevel.LEARNT_A_LOT.name())
    }

    def "#persists happiness level feedback into redis"() {
        given:
        final feedback = new Feedback(HappinessLevel.HAPPY, LearningLevel.LEARNT_A_LOT);

        when:
        unit.saveFeedback(feedback)

        then:
        assert redisTemplate.hasKey(HappinessLevel.HAPPY.name()) == true
    }

    def "#increments happiness level"() {
        given:
        final feedback = new Feedback(HappinessLevel.HAPPY, LearningLevel.LEARNT_A_LOT);

        when:
        2.times { unit.saveFeedback(feedback) }

        then:
        println redisTemplate.opsForValue().get(HappinessLevel.HAPPY.name())
        assert redisTemplate.opsForValue().get(HappinessLevel.HAPPY.name()) == 2
    }

    def "#persists learning level feedback into Redis"() {
        given:
        final feedback = new Feedback(HappinessLevel.HAPPY, LearningLevel.LEARNT_A_LOT);

        when:
        unit.saveFeedback(feedback)

        then:
        assert redisTemplate.hasKey(LearningLevel.LEARNT_A_LOT.name()) == true
    }
}