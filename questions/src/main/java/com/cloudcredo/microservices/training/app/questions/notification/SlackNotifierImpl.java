package com.cloudcredo.microservices.training.app.questions.notification;

import com.cloudcredo.microservices.training.app.questions.core.Question;
import org.springframework.stereotype.Service;

@Service
class SlackNotifierImpl implements Notifier {
  @Override
  public void newQuestionAsked(Question question) {

  }
}
