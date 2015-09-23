angular.module('questionsAndAnswers', ['questionsAndAnswersService'])
  .constant('QuestionsModel', {
      questions: []
    }
  )

  .controller('QuestionsAndAnswersController', function($scope, $interval, QuestionsModel, QuestionsAndAnswersService) {
    function updateQuestionsAndAnswers() {
      QuestionsAndAnswersService.getQuestions().$promise.then(function (response) {

        var questions = response.questions;

        questions.forEach(function (question) {
          var existingQuestion;
          question.answers = QuestionsAndAnswersService.getAnswers(question.id);
          existingQuestion = QuestionsModel.questions[question.id];
          question.displayExpanded = existingQuestion && existingQuestion.displayExpanded
        });

        QuestionsModel.questions = questions;
      });
    }

    $scope.questions = QuestionsModel.questions;

    $interval(updateQuestionsAndAnswers, 5000)
  });
