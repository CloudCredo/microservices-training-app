package com.cloudcredo.microservices.training.app.feedback.core

import com.cloudcredo.microservices.training.app.feedback.persistence.FeedbackPersistenceService
import spock.lang.Specification

class FeedbackServiceImplTest extends Specification {
  def feedbackPersistenceService = Mock(FeedbackPersistenceService)
  def feedbackService = new FeedbackServiceImpl(feedbackPersistenceService)

  def "GetAggregatedFeedback"() {
    given:
    def expectedFeedback = new AggregatedFeedback([(HappinessLevel.OKAY):1], [(LearningLevel.LEARNT_A_LITTLE): 1])

    when:
    def feedback = feedbackService.getAggregatedFeedback()

    then:
    1 * feedbackPersistenceService.getAggregatedFeedback() >> expectedFeedback
    feedback == expectedFeedback
  }

  def "StoreFeedback"() {
    given:
    def feedback = new Feedback()

    when:
    feedbackService.storeFeedback(feedback)

    then:
    feedbackPersistenceService.saveFeedback(feedback)
  }
}
