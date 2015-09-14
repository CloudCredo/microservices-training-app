package com.cloudcredo.microservices.training.app.core;

public class Feedback {
    private HappinessLevel happinessLevel;
    private LearningLevel learningLevel;

    public Feedback() {
    }

    public Feedback(HappinessLevel happinessLevel, LearningLevel learningLevel) {
        this.happinessLevel = happinessLevel;
        this.learningLevel = learningLevel;
    }

    public HappinessLevel getHappinessLevel() {
        return happinessLevel;
    }

    public void setHappinessLevel(HappinessLevel happinessLevel) {
        this.happinessLevel = happinessLevel;
    }

    public LearningLevel getLearningLevel() {
        return learningLevel;
    }

    public void setLearningLevel(LearningLevel learningLevel) {
        this.learningLevel = learningLevel;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "happinessLevel=" + happinessLevel +
                ", learningLevel=" + learningLevel +
                '}';
    }
}
