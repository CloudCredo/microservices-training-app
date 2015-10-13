package com.cloudcredo.microservices.training.app.feedback.context;

import com.cloudcredo.microservices.training.app.feedback.rest.RestProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class MicroservicesContext {
  @Bean
  public RestProxy questionsService(@Value("${vcap.services.questions.credentials.url}") String url) {
    return new RestProxy(URI.create("http://" + url));
  }

  @Bean
  public RestProxy myMicroservice(@Value("${MY_MICROSERVICE_URL:localhost}") String url) {
    return new RestProxy(URI.create("http://" + url));
  }
}
