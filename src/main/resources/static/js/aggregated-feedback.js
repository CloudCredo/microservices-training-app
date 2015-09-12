angular.module('aggregatedFeedback', ['feedbackService'])
  .value('AggregatedFeedbackModel', {
    happiness: [0, 0, 0, 0, 0],
    learning: [0, 0, 0]
  })

  .controller('AggregatedFeedbackController', function($scope, $interval, FeedbackService, AggregatedFeedbackModel) {
    function updateAggregatedFeedback() {
      //$scope.aggregatedFeedback = FeedbackService.getAggregatedFeedback();
    }

    $scope.aggregatedFeedback = AggregatedFeedbackModel;

    $interval(updateAggregatedFeedback, 3000)
  });