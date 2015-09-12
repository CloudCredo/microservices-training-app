package com.cloudcredo.microservices.training.app.rest;

import com.cloudcredo.microservices.training.app.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

  @Autowired
  FeedbackService feedbackService;

  @RequestMapping(method = RequestMethod.GET)
  public FeedbackGetResponse getFeedback() {
    AggregatedFeedback feedback = feedbackService.getAggregatedFeedback();
    FeedbackGetResponse response = FeedbackGetResponse.from(feedback);
    return response;
  }

  @RequestMapping(method = RequestMethod.POST)
  public FeedbackPostResponse getFeedback(FeedbackPostRequest request) {
    Feedback feedback = new Feedback();
    feedback.setHappinessLevel(HappinessLevel.values()[request.getHappinessLevel()]);
    feedback.setLearningLevel(LearningLevel.values()[request.getLearningLevel()]);

    feedbackService.storeFeedback(feedback);

    return FeedbackPostResponse.ok();
  }
}
