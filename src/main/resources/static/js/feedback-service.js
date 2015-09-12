angular.module('feedbackService', [])
  .factory('FeedbackService', function () {
    return {
      getAggregatedFeedback: function () {
        // TODO fetch current aggregated feedback from the server
      },

      submitFeedback: function (feedback) {
        // TODO submit a user's feedback to the server
      }
    }
  });