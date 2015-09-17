angular.module('feedbackService', ['aggregatedFeedback', 'ngResource'])
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
