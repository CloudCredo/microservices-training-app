angular.module('app', [
  'feedback',
  'questions',
  'myMicroservice',
  'asyncWorkers',

  // Third party libs
  'ngRoute',
  'frapontillo.gage'
]).config(function ($routeProvider) {
    $routeProvider.when('/', {
      templateUrl : 'templates/feedback-form.html',
      controller  : 'FeedbackFormController'
    }).when('/feedback-submitted/:page?', {
      templateUrl : 'templates/feedback-submitted.html',
      controller  : 'FeedbackSubmittedController'
    })
  })

  .controller('FeedbackSubmittedController', function ($scope, $routeParams) {
    $scope.page = $routeParams.page;
  });