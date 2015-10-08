package com.cloudcredo.microservices.training.app.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class QuestionsAndAnswersApplication {

  @Bean
  public Sampler<?> defaultSampler() {
    return new AlwaysSampler();
  }

  public static void main(String[] args) {
    SpringApplication.run(QuestionsAndAnswersApplication.class);
  }

}
