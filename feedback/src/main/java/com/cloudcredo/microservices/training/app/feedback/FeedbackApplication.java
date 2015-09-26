package com.cloudcredo.microservices.training.app.feedback;

import com.cloudcredo.microservices.training.app.feedback.context.MicroservicesContext;
import com.cloudcredo.microservices.training.app.feedback.context.RedisContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeedbackApplication {

  public static void main(String[] args) {
    Object[] sources = {
      FeedbackApplication.class,
      MicroservicesContext.class,
      RedisContext.class
    };
    SpringApplication.run(sources, args);
  }
}
