angular.module('feedbackForm', ['feedbackService', 'aggregatedFeedback'])
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
  });