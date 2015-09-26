package com.cloudcredo.microservices.training.app.feedback.core;

import com.cloudcredo.microservices.training.app.feedback.persistence.FeedbackPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
class FeedbackServiceImpl implements FeedbackService {
  private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

  private final FeedbackPersistenceService feedbackPersistenceService;

  @Autowired
  public FeedbackServiceImpl(@Qualifier("RedisFeedbackPersistenceService") FeedbackPersistenceService feedbackPersistenceService) {
    this.feedbackPersistenceService = feedbackPersistenceService;
  }

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
