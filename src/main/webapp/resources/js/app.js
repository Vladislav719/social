Array.prototype.remove = function(from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};

var app = angular.module('app', ['ngRoute', 'angularFileUpload', 'ngAnimate']);

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


app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);
