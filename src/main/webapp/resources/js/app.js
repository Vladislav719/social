var app = angular.module('app', ['ngRoute']);

app.service('api', function(){
    return{
        url: function(part){
            return part;
        }
    }
});

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'resources/parts/main.html',
            controller: 'MainPageCtrl',
            name: 'main'
        }).when('/login', {
            templateUrl: 'resources/parts/login.html',
            controller: 'MainPageCtrl',
            name: 'login'
        }).when('/registration',{
            templateUrl: 'resources/parts/registration.html',
            controller: 'MainPageCtrl',
            name: 'registration'
        }).otherwise({
            redirectTo:'/login'
        });

//    $locationProvider.html5Mode(true).hashPrefix('!');
});
/*
app.controller('MainCtrl', function($scope){

});*/


app.run(function($location, $rootScope, SignInInfo, api, $http){
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if (!SignInInfo.isLogin()){
            SignInInfo.setUser(localStorage.getItem('user'));
        }
        if (SignInInfo.isLogin()){
            if (next.name != 'account' && next.name != 'main'){
                $location.path('/account/students');
            }
        } else{
            if (next.name == 'account'){
                $location.path('/main');
            }
        }
    });
});

app.service('Status', function(){
   return {
       EMPTY: 0,
       OK: 200,
       BAD_REQUEST: 400
   }
});

app.service('SignInInfo', function($http, api, $location, Status){
    var user = null;
    return {
        getUser: function(){
            return user;
        },
        isLogin: function(){
            return !(user == null)
        },
        setUser: function(data){
            user = data;
        },
        login: function(user){
            var params = {j_username: user.email, j_password: user.password, 'remember-me': 'on'};
            $http.post('/j_spring_security_check', $.param(params),
                {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                .success(function(data){
                    console.log(data);
                    location.href = '/';
                }).error(function(data){
                    console.log(data);
                    location.href = '/login';
                })

        },
        logout: function(){
            localStorage.removeItem('social');
            user = null;
            $location.path('/');
        }
    }
});

app.controller('AppController', ['$scope', '$http', 'SignInInfo', function($scope, $http, SignInInfo){
    $scope.signInInfo = SignInInfo;
}]);

app.controller('MainPageCtrl', ['$scope', '$http', 'SignInInfo', function($scope, $http, SignInInfo){
    $scope.auth = SignInInfo.isLogin();
    $scope.shownedBlock = 'login';
    $scope.user = {};
    $scope.signUp = function(){
        $scope.errors = '';
        $http.post('/register_user', $scope.user).success(function(data){
            console.log(data);
            SignInInfo.setUser(data);
            SignInInfo.login($scope.user);
        }).error(function(data){
            $scope.errors = data.errors;
        });
    }
}]);

