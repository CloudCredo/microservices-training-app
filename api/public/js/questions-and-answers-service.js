angular.module('questionsAndAnswersService', ['ngResource'])
  .factory('QuestionsAndAnswersService', function ($resource) {
    var questionsEndpoint = $resource('/questions'),
      answersEndpoint = $resource('/questions/{questionId}/answers');

    return {
      getQuestions: function () {
        return questionsEndpoint.get()
      },

      submitQuestion: function (question) {
        questionsEndpoint.save(question)
      },

      getAnswers: function (questionId) {
        // TODO template in questionId
        return answersEndpoint.get()
      }
    }
  });
