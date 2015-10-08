angular.module('asyncWorkers', ['chart.js'])
  .directive('cnAsyncWorkers', function () {
    return {
      restrict: 'E',
      controller: 'AsyncWorkersController',
      templateUrl: 'templates/async-workers.html',
      scope: {}
    }
  })

  .controller('AsyncWorkersController', function ($scope, $timeout, AsyncWorkersService) {
    (function updateData() {
      function updateRequestMetadata(data) {
        $scope.requestData = data.requests;
      }

      function updateRequestRateChart(data) {
        function toWorkerName(workerData) {
          return workerData.name;
        }

        function toRequestRate(workerData) {
          return workerData.requestRate;
        }

        $scope.requestRateChartLabels = data.workers.map(toWorkerName);
        $scope.requestRateChartData = data.workers.map(toRequestRate);
      }

      AsyncWorkersService.getData().$promise.then(function (response) {
        updateRequestMetadata(response.data);
        updateRequestRateChart(response.data);

        $timeout(updateData, 5000)
      });
    })();
  })

  .service('AsyncWorkersService', function ($resource) {
    var workersService = $resource('/request-data');

    return {
      getData: function () {
        return workersService.get();
      }
    }
  });