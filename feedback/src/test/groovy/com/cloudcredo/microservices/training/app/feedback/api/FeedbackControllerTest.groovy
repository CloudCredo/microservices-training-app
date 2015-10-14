package com.cloudcredo.microservices.training.app.feedback.api

import com.cloudcredo.microservices.training.app.feedback.core.*
import spock.lang.Specification

class FeedbackControllerTest extends Specification {

  def feedbackService = Mock(FeedbackService)
  def feedbackController = new FeedbackController(feedbackService)

  def "GetFeedback"() {
    given:
    def expectedFeedback = new AggregatedFeedback(
      HappinessLevel.values().collectEntries { level -> [(level): level.ordinal()] },
      LearningLevel.values().collectEntries { level -> [(level): level.ordinal()] }
    )

    when:
    def response = feedbackController.getFeedback()

    then:
    1 * feedbackService.getAggregatedFeedback() >> expectedFeedback
    response.happiness == [0, 1, 2, 3, 4]
    response.learning == [0, 1, 2]
  }

  def "StoreFeedback"() {
    given:
    def request = new FeedbackPostRequest(happinessLevel: 2, learningLevel: 1)
    def expectedFeedback = new Feedback(
      happinessLevel: HappinessLevel.values()[2],
      learningLevel: LearningLevel.values()[1]
    )

    when:
    def response = feedbackController.storeFeedback(request)

    then:
    1 * feedbackService.storeFeedback(expectedFeedback)
    response.status == 'ok'
  }
}
