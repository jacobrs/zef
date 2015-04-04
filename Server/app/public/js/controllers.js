var appControllers = angular.module('appControllers', []);

appControllers.controller('AppCtrl', ['$scope', '$timeout', '$mdDialog', '$mdSidenav', '$http', '$location', '$mdToast', '$animate',
  function($scope, $timeout, $mdDialog, $mdSidenav, $http, $location, $mdToast, $animate) {
    $scope.toggleSidenav = function(menuId) {
      $mdSidenav(menuId).toggle();
    };

    $scope.public = function(){ // Get all public pics
      $http.get('/api/pictures/public').success(function(result) {      
        $scope.pictures = result;
      }).error(function(err) {

      });
      $scope.title = "Public Photos";
    };

    $scope.private = function(){ // Get all private pics
      $http.get('/api/pictures/').success(function(result) {      
        $scope.pictures = result;
      }).error(function(err) {

      });
      $scope.title = "My Photos";
    };

    $scope.viewPic = function(pic_id){
      $mdDialog.show({
        controller: ViewPicCtrl,
        templateUrl: 'templates/picture.tmpl.html',
        locals : {
                    pic_id: pic_id
                }
      })
      .then(function(answer) {
        $scope.alert = 'You said the information was "' + answer + '".';
      }, function() {
        $scope.alert = 'You cancelled the dialog.';
      });
  };

    $scope.private(); // Load all private by devailt    
     
  }
]);

function ViewPicCtrl($http, $scope, $mdDialog, pic_id) {
  var url = '/api/pictures/' + pic_id;
  console.log(url);
  $http.get('/api/pictures/' + pic_id).success(function(result) {      
    console.log(result);
    $scope.picture = result.picture;
    $scope.creator = result.account.username;
  }).error(function(err) {

  });

  $scope.hide = function() {
    $mdDialog.hide();
  };
  $scope.cancel = function() {
    $mdDialog.cancel();
  };
  $scope.answer = function(answer) {
    $mdDialog.hide(answer);
  };
}