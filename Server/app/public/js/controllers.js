var app = angular.module('ZefApp', ['ngMaterial']);

app.config(function ($mdThemingProvider) {
  $mdThemingProvider.definePalette('customPrimary', {  // teal
    '50':   'E0F2F1',
    '100':  'B2DFDB',
    '200':  '80CBC4',
    '300':  '4DB6AC',
    '400':  '26A69A',
    '500':  '009688',
    '600':  '00897B',
    '700':  '00796B',
    '800':  '00695C',
    '900':  '004D40',
    'A100': '009688',
    'A200': '009688',
    'A400': '009688',
    'A700': '009688',
    'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
                                        // on this palette should be dark or light
    'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
     '200', '300', '400', 'A100'],
    'contrastLightColors': undefined    // could also specify this if default was 'dark'
  });

  $mdThemingProvider.definePalette('customSecondary', { // purple
    '50':   'F3E5F5',
    '100':  'E1BEE7',
    '200':  'CE93D8',
    '300':  'BA68C8',
    '400':  'AB47BC',
    '500':  '9C27B0',
    '600':  '8E24AA',
    '700':  '7B1FA2',
    '800':  '6A1B9A',
    '900':  '4A148C',
    'A100': '9575CD',
    'A200': '9575CD',
    'A400': '9575CD',
    'A700': '9575CD',
    'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
                                        // on this palette should be dark or light
    'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
     '200', '300', '400', 'A100'],
    'contrastLightColors': undefined    // could also specify this if default was 'dark'
  });


  $mdThemingProvider.theme('default')
    .primaryPalette('customPrimary')
    .accentPalette('customSecondary');

});

app.controller('AppCtrl', ['$scope', '$mdSidenav', '$http', function($scope, $mdSidenav, $http){
  $scope.toggleSidenav = function(menuId) {
    $mdSidenav(menuId).toggle();
  };

  $http.get('/api/pictures/list').success(function(result) {      
    $scope.pictures = result;
  }).error(function(err) {
  });
   
}]);