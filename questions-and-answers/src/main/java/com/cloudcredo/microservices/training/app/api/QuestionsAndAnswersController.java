package com.cloudcredo.microservices.training.app.api;

import com.cloudcredo.microservices.training.app.core.Answer;
import com.cloudcredo.microservices.training.app.core.Question;
import com.cloudcredo.microservices.training.app.core.QuestionsAndAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionsAndAnswersController {

  private final QuestionsAndAnswersService questionsAndAnswersService;

  @Autowired
  public QuestionsAndAnswersController(QuestionsAndAnswersService questionsAndAnswersService) {
    this.questionsAndAnswersService = questionsAndAnswersService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public QuestionPostResponse askQuestion(@RequestBody QuestionPostRequest request) {
    Question question = new Question(request.getQuestion());
    questionsAndAnswersService.askQuestion(question);
    return QuestionPostResponse.ok();
  }

  @RequestMapping(method = RequestMethod.GET)
  public QuestionsGetResponse getQuestions() {
    List<Question> questions = questionsAndAnswersService.getAllQuestions();
    return QuestionsGetResponse.from(questions);
  }

  @RequestMapping(value = "{questionId}/answers", method = RequestMethod.POST)
  public void answerQuestion(@PathVariable long questionId, @RequestBody AnswerPostRequest request) {
    Answer answer = new Answer(questionId, request.getAnswer());
    questionsAndAnswersService.answerQuestion(answer);
  }


}
