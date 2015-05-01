var appControllers = angular.module('appControllers', []);

appControllers.controller('AppCtrl', ['$scope', '$timeout', '$mdDialog', '$mdSidenav', '$http', '$location', '$mdToast', '$animate',
  function($scope, $timeout, $mdDialog, $mdSidenav, $http, $location, $mdToast, $animate) {
    $scope.toggleSidenav = function(menuId) {
      $mdSidenav(menuId).toggle();
    };

    $scope.public = function(){ // Get all public pics
      $http.get('/api/pictures/public').success(function(result) { 
        if (isAuthed(result)){
          $scope.pictures = result.response;  
        }
        else{
          openLogin();
        }
      }).error(function(err) {

      });
      $scope.title = "Public Photos";
    };

    $scope.private = function(){ // Get all private pics
      $http.get('/api/pictures/').success(function(result) {
        if (isAuthed(result)){
          $scope.pictures = result.response;  
        }
        else{
          openLogin();
        }
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
        console.log(answer);
      }, function() {
        console.log('delcine');
      });
    };

    $scope.private(); // Load all private by devailt    

    function openLogin(){
      console.log('open');
      $mdDialog.show({
        templateUrl: 'templates/login.tmpl.html'
      })
      .then(function(answer) {
        console.log(answer);
      }, function() {
        console.log('delcine');
      });
    }
  }
]);

appControllers.controller('LoginCtrl', ['$rootScope', '$scope', '$location', '$localStorage', 'Auth', 
function($rootScope, $scope, $location, $localStorage, Auth) {
 
  $scope.user= {
    username: '',
    password: '',
    email: '',
    first: '',
    last: ''
  };

  $scope.signin = function() {
    var formData = {
      username: $scope.user.username,
      password: $scope.user.password
    };

    Auth.signin(formData, function(res) {
      if (res.status === 'failure') {
        alert(res.data)
      } else {
        $localStorage.token = res.token;
        window.location = "/";    
      }
    }, function() {
      $rootScope.error = 'Failed to signin';
    });
  };

  $scope.signup = function() {
    var formData = {
      username: $scope.username,
      first: $scope.first,
      last: $scope.last,
      email: $scope.email,
      password: $scope.password
    };

    Auth.save(formData, function(res) {
      if (res.type === false) {
        alert(res.data)
      } else {
        $localStorage.token = res.data.token;
        window.location = "/"   
      }
    }, function() {
      $rootScope.error = 'Failed to signup';
    })
  };

  $scope.me = function() {
    Auth.me(function(res) {
      $scope.myDetails = res;
    }, function() {
      $rootScope.error = 'Failed to fetch details';
    })
  };

  $scope.logout = function() {
    Auth.logout(function() {
      window.location = "/";
    }, function() {
      alert("Failed to logout!");
    });
  };
  $scope.token = $localStorage.token;
}]);


//AUTH
appControllers.factory('Auth', ['$http', '$localStorage', function($http, $localStorage){
  var baseUrl = "ec2-52-4-224-221.compute-1.amazonaws.com";
  function changeUser(user) {
    angular.extend(currentUser, user);
  }

  function urlBase64Decode(str) {
    var output = str.replace('-', '+').replace('_', '/');
    switch (output.length % 4) {
      case 0:
        break;
      case 2:
        output += '==';
        break;
      case 3:
        output += '=';
        break;
      default:
        throw 'Illegal base64url string!';
    }
    return window.atob(output);
  }

  function getUserFromToken() {
    var token = $localStorage.token;
    var user = {};
    if (typeof token !== 'undefined') {
      var encoded = token.split('.')[1];
      user = JSON.parse(urlBase64Decode(encoded));
    }
    return user;
  }

  var currentUser = getUserFromToken();

  return {
    save: function(data, success, error) {
      $http.put(baseUrl + '/api/accounts/', data).success(success).error(error);
    },
    signin: function(data, success, error) {
      console.log(baseUrl+'/api/accounts/');
      $http.post(baseUrl + '/api/accounts/', data).success(success).error(error);
    },
    me: function(success, error) {
      $http.get(baseUrl + '/me').success(success).error(error);
    },
    logout: function(success) {
      changeUser({});
      delete $localStorage.token;
      success();
    }
  };
}
]);

function isAuthed(res){
  var status = res.status || 'failed';
  return status == 'success';
}

function ViewPicCtrl($http, $scope, $mdDialog, pic_id) {
  var url = '/api/pictures/' + pic_id;
  console.log(url);
  $http.get('/api/pictures/' + pic_id).success(function(result) {      
    console.log(result);
    $scope.picture = result.picture;
    $scope.creator = result.account.username;

    var canvas = document.getElementById('canvas');
    var ctx=c.getContext("2d");
    ctx.rect(20,20,150,100);
    ctx.stroke();
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