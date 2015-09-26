package com.cloudcredo.microservices.training.app.feedback.api;

import com.cloudcredo.microservices.training.app.feedback.rest.RestProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionsServiceProxyController {

  private RestProxy questionsService;

  @Autowired
  public QuestionsServiceProxyController(@Qualifier("questionsService") RestProxy questionsService) {
    this.questionsService = questionsService;
  }

  @RequestMapping(value = "/questions/**", method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseEntity<String> proxy(RequestEntity<String> request) {
    return questionsService.proxy(request);
  }
}
