/**
 * Created by ElessarST on 14.02.2015.
 */

app.controller('LoginPageController', ['$scope', '$http', 'SignInInfo', 'UserApiService',
    '$location',
    function($scope, $http, SignInInfo, UserApiService, $location){
        $scope.auth = SignInInfo.isLogin();
        $scope.shownedBlock = 'login';
        $scope.user = {};
        $scope.authUser = {};
        $scope.signIn = function(){
            SignInInfo.login($scope.authUser).success(function(data){
                UserApiService.getCurrentUser().success(function(data) {
                    SignInInfo.setUser(data);
                    $location.path('/profile/' + SignInInfo.getUser().userId);
                });
            }).error(function (data) {
                $scope.loginError = true;
                if (data.loginError)
                    $scope.$emit('loginError');
            });
        };

        $scope.signUp = function(){
            $scope.errors = '';
            UserApiService.register($scope.user).success(function(data){
                console.log(data);
                SignInInfo.setUser(data);
                $scope.authUser = {
                    email: $scope.user.email,
                    password: $scope.user.password
                };
                $scope.signIn();
            }).error(function (data) {
                $scope.errors = data.errors;
                if (data.loginError)
                    $scope.$emit('loginError');
            });

        }
    }
]);
