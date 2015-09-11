angular.module('controllers', ['services'])
  .controller('FeedbackFormController', function($scope, FeedbackService) {
    function submitFeedback() {
      FeedbackService.submitFeedback($scope.feedback);
      $scope.feedbackSubmitted = true;
    }

    $scope.feedback = {
      happinessLevel: 0,
      learningLevel: 0
    };
    $scope.feedbackSubmitted = false;
    $scope.submitFeedback = submitFeedback;
  })

  .controller('AggregatedFeedbackController', function($scope, $interval, FeedbackService) {
    function updateAggregatedFeedback() {
      $scope.aggregatedFeedback = FeedbackService.getAggregatedFeedback();
    }

    $interval(updateAggregatedFeedback, 3000)
  });