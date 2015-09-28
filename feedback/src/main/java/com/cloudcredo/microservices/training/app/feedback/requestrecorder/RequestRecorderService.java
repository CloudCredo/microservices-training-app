package com.cloudcredo.microservices.training.app.feedback.requestrecorder;

public interface RequestRecorderService {
  void recordAPICall(String path, String method);
}
