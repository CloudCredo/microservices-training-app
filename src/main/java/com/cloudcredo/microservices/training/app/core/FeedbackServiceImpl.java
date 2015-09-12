package com.cloudcredo.microservices.training.app.core;

import com.cloudcredo.microservices.training.app.persistence.FeedbackPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FeedbackServiceImpl implements FeedbackService {
  @Autowired private FeedbackPersistenceService feedbackPersistenceService;
  private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public AggregatedFeedback getAggregatedFeedback() {
    return feedbackPersistenceService.getAggregatedFeedback();
  }

  @Override
  public void storeFeedback(Feedback feedback) {
    logger.info("Storing new feedback: {}", feedback);
    feedbackPersistenceService.saveFeedback(feedback);
  }
}
