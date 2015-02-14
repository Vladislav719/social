/**
 * Created by ElessarST on 14.02.2015.
 */

app.controller('LoginPageController', ['$scope', '$http', 'SignInInfo', 'UserApiService',
    function($scope, $http, SignInInfo, UserApiService){
        $scope.auth = SignInInfo.isLogin();
        $scope.shownedBlock = 'login';
        $scope.user = {};
        $scope.signUp = function(){
            $scope.errors = '';
            UserApiService.register($scope.user).success(function(data){
                console.log(data);
                SignInInfo.setUser(data);
                SignInInfo.login($scope.user);
            }).error(function(data){
                $scope.errors = data.errors;
            });
        }
    }
]);
