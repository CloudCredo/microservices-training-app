package com.cloudcredo.microservices.training.app.feedback.requestrecorder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMetadata {
  private final String method;
  private final String path;

  public RequestMetadata(@JsonProperty String method, @JsonProperty String path) {
    this.method = method;
    this.path = path;
  }

  public String getMethod() {
    return method;
  }

  public String getPath() {
    return path;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RequestMetadata that = (RequestMetadata) o;

    if (method != null ? !method.equals(that.method) : that.method != null) return false;
    return !(path != null ? !path.equals(that.path) : that.path != null);

  }

  @Override
  public int hashCode() {
    int result = method != null ? method.hashCode() : 0;
    result = 31 * result + (path != null ? path.hashCode() : 0);
    return result;
  }
}
