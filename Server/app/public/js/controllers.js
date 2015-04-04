var appControllers = angular.module('appControllers', []);

appControllers.controller('AppCtrl', ['$scope', '$timeout', '$mdSidenav', '$http', '$location', '$mdToast', '$animate',
  function($scope, $timeout, $mdSidenav, $http, $location, $mdToast, $animate) {
    $scope.toggleSidenav = function(menuId) {
      $mdSidenav(menuId).toggle();
    };

    $http.get('/api/pictures/public').success(function(result) {      
      $scope.pictures = result;
      console.log(result);
    }).error(function(err) {

    });
     
  }
]);