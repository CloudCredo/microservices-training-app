angular.module('aggregatedFeedback', ['feedbackService'])
  .value('AggregatedFeedbackModel', {
    feedback: {
      happiness: [0, 0, 0, 0, 0],
      learning: [0, 0, 0]
    }
  })

  .controller('AggregatedFeedbackController', function($scope, $interval, FeedbackService, AggregatedFeedbackModel) {
    function updateAggregatedFeedback() {
      AggregatedFeedbackModel.feedback = FeedbackService.getAggregatedFeedback();
    }

    $scope.aggregatedFeedback = AggregatedFeedbackModel.feedback;

    $interval(updateAggregatedFeedback, 3000)
  });