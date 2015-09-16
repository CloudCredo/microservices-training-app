angular.module('aggregatedFeedback', ['feedbackService', 'feedbackLevels', 'feedbackForm'])
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

  .controller('AggregatedFeedbackController', function($scope, $interval, FeedbackService, AggregatedFeedbackModel, FeedbackSubmissionModel) {
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
    $scope.feedbackSubmission = FeedbackSubmissionModel;

    $interval(updateAggregatedFeedback, 1000)
  });