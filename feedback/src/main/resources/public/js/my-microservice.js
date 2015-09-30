angular.module('myMicroservice', [])
  .directive('cnMyMicroservice', function () {
    return {
      restrict: 'E',
      controller: 'MyMicroserviceController',
      templateUrl: 'templates/my-microservice.html',
      scope: {}
    }
  })

  .controller('MyMicroserviceController', function ($scope, $timeout, MyMicroserviceService) {
    (function fetchData() {
      $scope.microserviceData = MyMicroserviceService.getData();

      $timeout(5000, fetchData());
    })()
  })

  .service('MyMicroserviceService', function ($resource) {
    var myMicroservice = $resource('/my-microservice');

    return {
      getData: function () {
        myMicroservice.get();
      }
    }
  });

