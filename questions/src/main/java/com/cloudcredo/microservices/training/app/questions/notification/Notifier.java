package com.cloudcredo.microservices.training.app.questions.notification;

import com.cloudcredo.microservices.training.app.questions.core.Question;

public interface Notifier {

  void newQuestionAsked(Question question);

}
