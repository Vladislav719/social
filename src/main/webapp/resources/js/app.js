var app = angular.module('app', ['ngRoute']);

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            redirectTo:'/login'
        }).when('/profile/:profileId', {
            templateUrl: 'resources/parts/profile.html',
            controller: 'ProfileController',
            name: 'auth'
        }).when('/login', {
            templateUrl: 'resources/parts/login.html',
            controller: 'LoginPageController',
            name: 'notAuth'
        }).otherwise({
            redirectTo:'/login'
        });

//    $locationProvider.html5Mode(true).hashPrefix('!');
});


app.run(function($location, $rootScope, SignInInfo, $http){
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if (!SignInInfo.checkRoute(next))
            $location.path(SignInInfo.redirect());
    });
});

