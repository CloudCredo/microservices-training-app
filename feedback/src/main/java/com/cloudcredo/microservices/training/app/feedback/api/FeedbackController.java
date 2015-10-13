package com.cloudcredo.microservices.training.app.feedback.api;

import com.cloudcredo.microservices.training.app.feedback.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

  private FeedbackService feedbackService;

  @Autowired
  public FeedbackController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public FeedbackGetResponse getFeedback() {
    AggregatedFeedback feedback = feedbackService.getAggregatedFeedback();
    return FeedbackGetResponse.from(feedback);
  }

  @RequestMapping(method = RequestMethod.POST)
  public FeedbackPostResponse storeFeedback(@RequestBody FeedbackPostRequest request) {
    Feedback feedback = new Feedback();
    feedback.setHappinessLevel(HappinessLevel.values()[request.getHappinessLevel()]);
    feedback.setLearningLevel(LearningLevel.values()[request.getLearningLevel()]);

    feedbackService.storeFeedback(feedback);

    return FeedbackPostResponse.ok();
  }
}
