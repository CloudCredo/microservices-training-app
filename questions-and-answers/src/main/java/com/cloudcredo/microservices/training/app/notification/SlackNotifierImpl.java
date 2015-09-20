package com.cloudcredo.microservices.training.app.notification;

import com.cloudcredo.microservices.training.app.core.Question;
import org.springframework.stereotype.Service;

@Service
class SlackNotifierImpl implements Notifier {
  @Override
  public void newQuestionAsked(Question question) {

  }
}
