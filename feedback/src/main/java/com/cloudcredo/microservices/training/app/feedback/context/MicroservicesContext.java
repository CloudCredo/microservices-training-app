package com.cloudcredo.microservices.training.app.feedback.context;

import com.cloudcredo.microservices.training.app.feedback.rest.RestProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class MicroservicesContext {
  @Bean
  public RestProxy questionsService(@Value("${microservices.questions.url}") URI url) {
    return new RestProxy(url);
  }
}
