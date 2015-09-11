package com.cloudcredo.microservices.training.app.rest;

import com.cloudcredo.microservices.training.app.core.Feedback;
import com.cloudcredo.microservices.training.app.core.FeedbackService;
import com.cloudcredo.microservices.training.app.core.HappinessLevel;
import com.cloudcredo.microservices.training.app.core.LearningLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public FeedbackGetResponse getFeedback() {
        FeedbackGetResponse response = new FeedbackGetResponse();

        return response;
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public FeedbackPostResponse getFeedback(FeedbackPostRequest request) {
        Feedback feedback = new Feedback();
        feedback.setHappinessLevel(HappinessLevel.values()[request.happinessLevel]);
        feedback.setLearningLevel(LearningLevel.values()[request.learningLevel]);

        feedbackService.storeFeedback(feedback);

        return FeedbackPostResponse.ok();
    }
}
