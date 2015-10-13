package com.cloudcredo.microservices.training.app.persistence;

import com.cloudcredo.microservices.training.app.core.AggregatedFeedback;
import com.cloudcredo.microservices.training.app.core.Feedback;

/**
 * Created by work on 11/09/15.
 */
public interface FeedbackPersistenceService {
  AggregatedFeedback getAggregatedFeedback();

  void saveFeedback(Feedback feedback);
}
