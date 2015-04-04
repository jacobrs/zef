var appControllers = angular.module('appControllers', []);

appControllers.controller('AppCtrl', ['$scope', '$timeout', '$mdSidenav', '$http', '$location', '$mdToast', '$animate',
  function($scope, $timeout, $mdSidenav, $http, $location, $mdToast, $animate) {
    $scope.toggleSidenav = function(menuId) {
      $mdSidenav(menuId).toggle();
    };

    $scope.public = function(){ // Get all public pics
      $http.get('/api/pictures/public').success(function(result) {      
        $scope.pictures = result;
        console.log(result);
      }).error(function(err) {

      });
    };

    $scope.private = function(){ // Get all private pics
      $http.get('/api/pictures/').success(function(result) {      
        $scope.pictures = result;
        console.log(result);
      }).error(function(err) {

      });
    };

    $scope.public(); // Load all public by devailt    
     
  }
]);