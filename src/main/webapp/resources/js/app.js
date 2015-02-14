var app = angular.module('app', ['ngRoute']);

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'resources/parts/main.html',
            controller: 'AppController',
            name: 'main'
        }).when('/profile/:profileId', {
            templateUrl: 'resources/parts/profile.html',
            controller: 'ProfileController'
        }).when('/login', {
            templateUrl: 'resources/parts/login.html',
            controller: 'LoginPageController',
            name: 'login'
        }).otherwise({
            redirectTo:'/login'
        });

//    $locationProvider.html5Mode(true).hashPrefix('!');
});



app.run(function($location, $rootScope, SignInInfo, $http){
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if (!SignInInfo.isLogin()){
            SignInInfo.updateUser();
        }
    });
});

