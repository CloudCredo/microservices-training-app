package com.cloudcredo.microservices.training.app.core;

/**
 * Created by work on 11/09/15.
 */
public class Feedback {
    private LearningLevel learningLevel;
    private HappinessLevel happinessLevel;

    public LearningLevel getLearningLevel() {
        return learningLevel;
    }

    public void setLearningLevel(LearningLevel learningLevel) {
        this.learningLevel = learningLevel;
    }

    public HappinessLevel getHappinessLevel() {
        return happinessLevel;
    }

    public void setHappinessLevel(HappinessLevel happinessLevel) {
        this.happinessLevel = happinessLevel;
    }
}
