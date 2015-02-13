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
            user = JSON.parse(data);
        },
        login: function(response){
            $http.post(api.url('sessions.json'), {email: response.email, password: response.password}).success(function(data){
                localStorage.setItem('social', data);
                user = JSON.parse(data.user);
                user.group_id = data.group_id;
                localStorage.setItem('AdditionalPointsUser', JSON.stringify(user));
                $location.url('/account/students');
            }).error(function(data){
                alert('wrong');
            });

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
            SignInInfo.login(data);
        }).error(function(data){
            $scope.errors = data.errors;
        });
    }
}]);

