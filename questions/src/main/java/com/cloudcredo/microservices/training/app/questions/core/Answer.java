package com.cloudcredo.microservices.training.app.questions.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Answer {
  private final long questionId;
  private final String answer;

  public Answer(@JsonProperty("questionId") long questionId,
                @JsonProperty("answer") String answer) {
    this.questionId = questionId;
    this.answer = answer;
  }

  public long getQuestionId() {
    return questionId;
  }

  public String getAnswer() {
    return answer;
  }

  @Override
  public String toString() {
    return "Answer{" +
      "questionId=" + questionId +
      ", answer='" + answer + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Answer answer1 = (Answer) o;

    if (questionId != answer1.questionId) return false;
    return !(answer != null ? !answer.equals(answer1.answer) : answer1.answer != null);

  }

  @Override
  public int hashCode() {
    int result = (int) (questionId ^ (questionId >>> 32));
    result = 31 * result + (answer != null ? answer.hashCode() : 0);
    return result;
  }
}
