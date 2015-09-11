package microservices.training.app.persistence;

import microservices.training.app.core.Feedback;

/**
 * Created by work on 11/09/15.
 */
public interface FeedbackPersistenceService {
    // TODO get feedback
    void saveFeedback(Feedback feedback);
}
