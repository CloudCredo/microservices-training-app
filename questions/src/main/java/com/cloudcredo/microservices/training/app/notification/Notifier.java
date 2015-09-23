package com.cloudcredo.microservices.training.app.notification;

import com.cloudcredo.microservices.training.app.core.Question;

public interface Notifier {

  void newQuestionAsked(Question question);

}
