angular.module('app', [
  'feedback',

  // Third party libs
  'ngRoute',
  'frapontillo.gage'
]).config(function ($routeProvider) {
  $routeProvider.when('/', {
    templateUrl : 'templates/feedback-form.html',
    controller  : 'FeedbackFormController'
  }).when('/aggregated-feedback', {
    templateUrl : 'templates/aggregated-feedback.html',
    controller  : 'AggregatedFeedbackController'
  })
});