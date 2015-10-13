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
      MyMicroserviceService.getData().$promise.then(function (data) {
        $scope.microserviceData = data;
      });

      $timeout(fetchData, 5000);
    })();
  })

  .service('MyMicroserviceService', function ($resource) {
    var myMicroservice = $resource('/my-microservice');

    return {
      getData: function () {
        return myMicroservice.get();
      }
    }
  });

