angular.module('aggregatedFeedback', ['feedbackService', 'feedbackLevels', 'feedbackForm'])
  .factory('AggregatedFeedbackModel', function (FeedbackLevels) {
    function toModel(level) {
      return {
        description: level.description,
        votePercentage: 0
      }
    }

    return {
      happinessLevels: FeedbackLevels.happinessLevels.map(toModel),
      learningLevels: FeedbackLevels.learningLevels.map(toModel)
    }
  })

  .controller('AggregatedFeedbackController', function($scope, $interval, FeedbackService, AggregatedFeedbackModel, FeedbackSubmissionModel) {
    function updateAggregatedFeedback() {
      function totalVotes(voteArray) {
        return voteArray.reduce(function (sum, value) {
          return sum + value;
        })
      }

      function updateModelVotes(modelArray, voteArray) {
        var numberOfVotes = totalVotes(voteArray);
        if (numberOfVotes === 0) return;

        for (var i = 0; i < modelArray.length; i++) {
          modelArray[i].votePercentage = voteArray[i] * 100 / numberOfVotes;
        }
      }

      FeedbackService.getAggregatedFeedback().$promise.then(function (feedback) {
        updateModelVotes(AggregatedFeedbackModel.happinessLevels, feedback.happiness);
        updateModelVotes(AggregatedFeedbackModel.learningLevels, feedback.learning);
      });
    }

    $scope.aggregatedFeedback = AggregatedFeedbackModel;
    $scope.feedbackSubmission = FeedbackSubmissionModel;
    $interval(updateAggregatedFeedback, 1000)
  });