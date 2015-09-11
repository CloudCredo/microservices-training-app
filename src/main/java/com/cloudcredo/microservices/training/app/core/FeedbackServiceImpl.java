package com.cloudcredo.microservices.training.app.core;

import com.cloudcredo.microservices.training.app.persistence.FeedbackPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by work on 11/09/15.
 */
@Service
class FeedbackServiceImpl implements FeedbackService {
    @Autowired private FeedbackPersistenceService feedbackPersistenceService;

    @Override
    public AggregatedFeedback getAggregatedFeedback() {
        // TODO
        return null;
    }

    @Override
    public void storeFeedback(Feedback feedback) {
        feedbackPersistenceService.saveFeedback(feedback);
    }
}
