package com.cloudcredo.microservices.training.app.questions.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {

  private final Long id;
  private final String question;

  public Question(String question) {
    this(null, question);
  }

  public Question(Long id, Question other) {
    this(id, other.question);
  }

  public Question(@JsonProperty("id") Long id,
                  @JsonProperty("question") String question) {
    this.id = id;
    this.question = question;
  }

  public Long getId() {
    return id;
  }

  public String getQuestion() {
    return question;
  }

  @Override
  public String toString() {
    return "Question{" +
      "id=" + id +
      ", question='" + question + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Question question1 = (Question) o;

    if (id != null ? !id.equals(question1.id) : question1.id != null) return false;
    return !(question != null ? !question.equals(question1.question) : question1.question != null);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (question != null ? question.hashCode() : 0);
    return result;
  }
}
