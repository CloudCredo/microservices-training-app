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
      function updateRequestMetadata(requests) {
        function interestingPaths(path) {
          return path.search(/bower|templates|js|favicon/) === -1 && path !== '/';
        }

        var methods = Object.keys(requests);

        var allPaths = Object.keys(methods.reduce(function (paths, method) {
          Object.keys(requests[method]).forEach(function (path) {
            paths[path] = null;
          });

          return paths;
        }, {})).filter(interestingPaths).sort();

        var chartData = methods.map(function (method) {
          return allPaths.map(function (path) {
            return requests[method][path] || 0;
          });
        });

        $scope.requestTypeChartData = chartData;
        $scope.requestTypeChartLabels = allPaths;
        $scope.requestTypeChartSeries = methods;
      }

      AsyncWorkersService.getData().$promise.then(function (response) {
        updateRequestMetadata(response.requests);

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