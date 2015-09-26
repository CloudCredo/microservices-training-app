angular.module('feedback', ['ngResource'])
  .factory('FeedbackLevels', function () {

    var darkRed = '#bf0000',
      lightRed = '#ff0000',
      darkOrange = '#e49902',
      lightOrange = '#ffe600',
      darkGreen = '#85cc1a',
      lightGreen = '#a6ff21';

    return {
      happinessLevels: [
        {index: 0, description: "This sucks!", gaugeColours: [darkRed, lightRed]},
        {index: 1, description: "Not great", gaugeColours: [darkRed, lightRed]},
        {index: 2, description: "Meh", gaugeColours: [darkOrange, lightOrange]},
        {index: 3, description: "Pretty good", gaugeColours: [darkGreen, lightGreen]},
        {index: 4, description: "Awesome!", gaugeColours: [darkGreen, lightGreen]}
      ],
      learningLevels: [
        {index: 0, description: "I've learnt nothing!", gaugeColours: [darkRed, lightRed]},
        {index: 1, description: "I've learnt something", gaugeColours: [darkOrange, lightOrange]},
        {index: 2, description: "Mind blown!", gaugeColours: [darkGreen, lightGreen]}
      ]
    }
  })

  .controller('FeedbackFormController', function($scope, $location, FeedbackService, FeedbackLevels) {
    function submitFeedback() {
      FeedbackService.submitFeedback($scope.feedback);
      $location.path('/feedback-submitted')
    }

    $scope.feedback = {
      happinessLevel: 2,
      learningLevel: 1
    };
    $scope.feedbackLevels = FeedbackLevels;
    $scope.submitFeedback = submitFeedback;
  })

  .factory('AggregatedFeedbackModel', function (FeedbackLevels) {
    function toModel(level) {
      return angular.extend({ votePercentage: 0 }, level);
    }

    return {
      happinessLevels: FeedbackLevels.happinessLevels.map(toModel),
      learningLevels: FeedbackLevels.learningLevels.map(toModel),
      totalVotes: 0
    }
  })

  .directive('cnAggregatedFeedback', function() {
    return {
      scope: {},
      restrict: 'E',
      controller: 'AggregatedFeedbackController',
      templateUrl: 'templates/aggregated-feedback.html'
    }
  })

  .controller('AggregatedFeedbackController', function($scope, $interval, FeedbackService, AggregatedFeedbackModel) {
    function updateAggregatedFeedback() {
      function sumVotes(voteArray) {
        return voteArray.reduce(function (sum, value) {
          return sum + value;
        })
      }

      function updateModelVotes(modelArray, voteArray) {
        // Arbitrary vote array can be used to calculate total votes since
        // it's not possible to vote without specifying a value for all vote
        // types
        var totalVotes = sumVotes(voteArray);
        AggregatedFeedbackModel.totalVotes = totalVotes;

        if (totalVotes === 0) return;

        for (var i = 0; i < modelArray.length; i++) {
          modelArray[i].votePercentage = voteArray[i] * 100 / totalVotes;
        }
      }

      FeedbackService.getAggregatedFeedback().$promise.then(function (feedback) {
        updateModelVotes(AggregatedFeedbackModel.happinessLevels, feedback.happiness);
        updateModelVotes(AggregatedFeedbackModel.learningLevels, feedback.learning);
      });
    }

    $scope.aggregatedFeedback = AggregatedFeedbackModel;

    $interval(updateAggregatedFeedback, 1000)
  })

  .factory('FeedbackService', function ($resource) {
    var feedback_endpoint = $resource('/feedback');

    return {
      getAggregatedFeedback: function () {
        return feedback_endpoint.get()
      },

      submitFeedback: function (feedback) {
        feedback_endpoint.save(feedback)
      }
    }
  });