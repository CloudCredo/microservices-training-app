package com.cloudcredo.microservices.training.app.feedback;

import com.cloudcredo.microservices.training.app.feedback.context.MicroservicesContext;
import com.cloudcredo.microservices.training.app.feedback.context.RedisContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class FeedbackApplication {

  @Bean
  public Sampler<?> defaultSampler() {
    return new AlwaysSampler();
  }

  public static void main(String[] args) {
    Object[] sources = {
            FeedbackApplication.class,
            MicroservicesContext.class,
            RedisContext.class
    };
    SpringApplication.run(sources, args);
  }
}
