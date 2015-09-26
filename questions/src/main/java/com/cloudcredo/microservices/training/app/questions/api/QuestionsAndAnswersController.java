package com.cloudcredo.microservices.training.app.questions.api;

import com.cloudcredo.microservices.training.app.questions.core.Answer;
import com.cloudcredo.microservices.training.app.questions.core.Question;
import com.cloudcredo.microservices.training.app.questions.core.QuestionsAndAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/questions")
public class QuestionsAndAnswersController {

  private final QuestionsAndAnswersService questionsAndAnswersService;

  @Autowired
  public QuestionsAndAnswersController(QuestionsAndAnswersService questionsAndAnswersService) {
    this.questionsAndAnswersService = questionsAndAnswersService;
  }

  @RequestMapping(method = POST)
  public QuestionPostResponse askQuestion(@RequestBody QuestionPostRequest request) {
    Question question = new Question(request.getQuestion());
    questionsAndAnswersService.askQuestion(question);
    return QuestionPostResponse.ok();
  }

  @RequestMapping(method = GET)
  public QuestionsGetResponse getQuestions() {
    List<Question> questions = questionsAndAnswersService.getAllQuestions();
    return QuestionsGetResponse.from(questions);
  }

  @RequestMapping(value = "{questionId}/answers", method = POST)
  public void answerQuestion(@PathVariable long questionId, @RequestBody AnswerPostRequest request) {
    Answer answer = new Answer(questionId, request.getAnswer());
    questionsAndAnswersService.answerQuestion(answer);
  }

  @RequestMapping(value = "{questionId}/answers", method = GET)
  public AnswersGetResponse answersForQuestion(@PathVariable long questionId) {
    List<Answer> answers = questionsAndAnswersService.getAnswersForQuestion(questionId);
    return AnswersGetResponse.from(answers);
  }
}
