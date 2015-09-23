angular.module('questionForm', ['questionsAndAnswersService'])
  .controller('QuestionFormController', function($scope, QuestionsAndAnswersService) {
    function submitQuestion() {
      QuestionsAndAnswersService.submitQuestion($scope.question);
    }

    $scope.question = '';
    $scope.submitQuestion = submitQuestion;
  });
