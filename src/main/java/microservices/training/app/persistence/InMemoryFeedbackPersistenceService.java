package microservices.training.app.persistence;

import microservices.training.app.core.Feedback;
import microservices.training.app.core.HappinessLevel;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by work on 11/09/15.
 */
@Service
class InMemoryFeedbackPersistenceService implements FeedbackPersistenceService {
    private ConcurrentMap<HappinessLevel, Integer> happinessLevels = new ConcurrentHashMap<>();
    private ConcurrentMap<HappinessLevel, Integer> learningLevels = new ConcurrentHashMap<>();


    @Override
    public void saveFeedback(Feedback feedback) {
        // TODO
    }
}
