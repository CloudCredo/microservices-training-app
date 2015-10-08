package com.cloudcredo.microservices.training.app.feedback.context;

import com.cloudcredo.microservices.training.app.feedback.rest.RestProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
public class MicroservicesContext {

  @Autowired(required = true)
  private RestTemplate restTemplate;

  @Bean
  public RestProxy questionsService(@Value("${vcap.services.questions.credentials.url}") String url) {
    return new RestProxy(URI.create("http://" + url), restTemplate);
  }

  @Bean
  public RestProxy myMicroservice(@Value("${MY_MICROSERVICE_URL}") String url) {
    return new RestProxy(URI.create("http://" + url), restTemplate);
  }

  @Bean
  public RestProxy requestDataService(@Value("${vcap.services.request-data.credentials.url}") String url) {
    return new RestProxy(URI.create("http://" + url), restTemplate);
  }
}
