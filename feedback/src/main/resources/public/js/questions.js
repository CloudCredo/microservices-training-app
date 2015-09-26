angular.module('questions', ['ngResource'])
  //.constant('QuestionsModel', {
  //  questions: []
  //})

  .directive('cnQuestionForm', function () {
    return {
      scope: {},
      restrict: 'E',
      controller: 'QuestionFormController',
      templateUrl: 'templates/question-form.html'
    }
  })
  .controller('QuestionFormController', function($scope, QuestionsAndAnswersService) {
    function submitQuestion() {
      if ($scope.question.length == 0) {
        return;
      }

      QuestionsAndAnswersService.submitQuestion($scope.question).$promise.then(function () {
        $scope.question = '';
      });
    }

    $scope.question = '';
    $scope.submitQuestion = submitQuestion;
  })

  .directive('cnQuestionsAndAnswers', function () {
    return {
      scope: {},
      restrict: 'E',
      controller: 'QuestionsAndAnswersController',
      templateUrl: 'templates/questions-and-answers.html'
    }
  })
  .controller('QuestionsAndAnswersController', function($scope, $timeout, QuestionsAndAnswersService) {
    (function updateQuestionsAndAnswers() {
      QuestionsAndAnswersService.getQuestions().$promise.then(function (questionsResponse) {
        var updatedQuestions = questionsResponse.questions;

        updatedQuestions.forEach(function (updatedQuestion) {
          QuestionsAndAnswersService.getAnswers(updatedQuestion.id).$promise.then(function (answersResponse) {
            var existingQuestion = $scope.questions[updatedQuestion.id];
            updatedQuestion.answers = answersResponse.answers;
            updatedQuestion.myAnswer = existingQuestion ? existingQuestion.myAnswer : '';
            $scope.questions[updatedQuestion.id] = updatedQuestion;
          })
        });

        $timeout(updateQuestionsAndAnswers, 5000);
      });
    })();

    function submitAnswer(question) {
      QuestionsAndAnswersService.submitAnswer(question.id, question.myAnswer).$promise.then(function () {
        question.answers.push({answer: question.myAnswer});
        question.myAnswer = '';
      });
    }

    $scope.questions = [];
    $scope.submitAnswer = submitAnswer;
    $scope.defined = function (value) {
      return value;
    };
  })

  .factory('QuestionsAndAnswersService', function ($resource) {
    var questionsEndpoint = $resource('/questions'),
      answersEndpoint = $resource('/questions/:questionId/answers');

    return {
      getQuestions: function () {
        return questionsEndpoint.get()
      },

      submitQuestion: function (question) {
        return questionsEndpoint.save({question: question})
      },

      submitAnswer: function (questionId, answer) {
        return answersEndpoint.save({questionId: questionId}, {questionId: questionId, answer: answer})
      },

      getAnswers: function (questionId) {
        return answersEndpoint.get({questionId: questionId})
      }
    }
  });
