package com.cloudcredo.microservices.training.app.questions.persistence;

import com.cloudcredo.microservices.training.app.questions.core.Answer;
import com.cloudcredo.microservices.training.app.questions.core.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class RedisQuestionAndAnswersPersistenceService implements QuestionAndAnswersPersistenceService {

  private static final String NEXT_QUESTION_ID_KEY = "questions:next-id";
  private static final String ALL_QUESTION_KEYS_KEY = "questions:all-ids";

  private final StringRedisTemplate stringRedisTemplate;
  private final RedisTemplate<String, Question> questionRedisTemplate;
  private final RedisTemplate<String, Answer> answerRedisTemplate;

  @Autowired
  public RedisQuestionAndAnswersPersistenceService(
    StringRedisTemplate stringRedisTemplate,
    RedisTemplate<String, Question> questionRedisTemplate,
    RedisTemplate<String, Answer> answerRedisTemplate
  ) {
    this.stringRedisTemplate = stringRedisTemplate;
    this.questionRedisTemplate = questionRedisTemplate;
    this.answerRedisTemplate = answerRedisTemplate;
  }

  @Override
  public Question saveQuestion(Question question) {
    final long nextId = stringRedisTemplate.opsForValue().increment(NEXT_QUESTION_ID_KEY, 1);
    question = new Question(nextId, question);

    final String questionKey = questionKey(question);
    questionRedisTemplate.opsForValue().set(questionKey, question);
    stringRedisTemplate.opsForList().rightPush(ALL_QUESTION_KEYS_KEY, questionKey);

    return question;
  }

  @Override
  public List<Question> getAllQuestions() {
    // Assuming here that our set of questions is small enough to fit into memory!
    List<String> allQuestionKeys = stringRedisTemplate.opsForList().range(ALL_QUESTION_KEYS_KEY, 0, -1);

    return allQuestionKeys.stream()
      .map((key) -> questionRedisTemplate.opsForValue().get(key))
      .collect(Collectors.toList());
  }

  @Override
  public void saveAnswer(Answer answer) {
    answerRedisTemplate.opsForList().rightPush(answerKey(answer), answer);
  }

  @Override
  public List<Answer> getAnswersToQuestion(long questionId) {
    return answerRedisTemplate.opsForList().range(answerKey(questionId), 0, -1);
  }

  private static String questionKey(Question question) {
    if (question.getId() == null) throw new IllegalArgumentException("Can't generate a key for a question with no id");
    return "question#" + question.getId();
  }

  private static String answerKey(Answer answer) {
    return answerKey(answer.getQuestionId());
  }

  private static String answerKey(long questionId) {
    return "answers:question#" + questionId;
  }
}
