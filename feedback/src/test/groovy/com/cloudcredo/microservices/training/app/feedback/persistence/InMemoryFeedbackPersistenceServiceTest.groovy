package com.cloudcredo.microservices.training.app.feedback.persistence

import com.cloudcredo.microservices.training.app.feedback.core.Feedback
import com.cloudcredo.microservices.training.app.feedback.core.HappinessLevel
import com.cloudcredo.microservices.training.app.feedback.core.LearningLevel
import spock.lang.Specification

class InMemoryFeedbackPersistenceServiceTest extends Specification {
  FeedbackPersistenceService persistenceService

  def setup() {
    persistenceService = new InMemoryFeedbackPersistenceService()
  }

  def "is initially empty"() {
    when:
    def aggregatedFeedback = persistenceService.getAggregatedFeedback()

    then:
    assert aggregatedFeedback.happinessLevels.isEmpty()
    assert aggregatedFeedback.learningLevels.isEmpty()
  }

  def "can save and retrieve data"() {
    when:
    HappinessLevel.values().each { happiness ->
      LearningLevel.values().each { learning ->
        def feedback = new Feedback(happinessLevel: happiness, learningLevel: learning)
        persistenceService.saveFeedback(feedback)
      }
    }
    def aggregatedFeedback = persistenceService.getAggregatedFeedback()

    then:
    aggregatedFeedback.happinessLevels == HappinessLevel.values().collectEntries { level -> [(level): 3] }
    aggregatedFeedback.learningLevels == LearningLevel.values().collectEntries { level -> [(level): 5]}
  }
}
