package com.cloudcredo.microservices.training.app.feedback.core;

/**
 * Created by work on 11/09/15.
 */
public interface FeedbackService {

  AggregatedFeedback getAggregatedFeedback();

  void storeFeedback(Feedback feedback);

}
