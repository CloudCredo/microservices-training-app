angular.module('feedbackForm', ['feedbackService', 'aggregatedFeedback', 'feedbackLevels'])
  .constant('FeedbackSubmissionModel', {
    submitted: false
  })

  .controller('FeedbackFormController', function($scope, FeedbackService, FeedbackLevels, FeedbackSubmissionModel) {
    function submitFeedback() {
      FeedbackService.submitFeedback($scope.feedback);
      FeedbackSubmissionModel.submitted = true;
    }

    $scope.feedback = {
      happinessLevel: 2,
      learningLevel: 1
    };
    $scope.feedbackLevels = FeedbackLevels;
    $scope.feedbackSubmission = FeedbackSubmissionModel;
    $scope.submitFeedback = submitFeedback;
  });