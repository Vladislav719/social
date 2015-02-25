/**
 * Created by ElessarST on 24.02.2015.
 */
app.controller('EditController', function($scope, $http, SignInInfo, UserApiService, $location){
    $scope.user = $scope.my;
    $scope.pages.currentPage = 'profile';
    $scope.$on('userInfoUpdate', function(){
        $scope.user = $scope.my;
    });
    $scope.updateUserInfo = function () {
        var profile = $scope.user.userInfo;
        delete profile.user;
        UserApiService.updateUserInfo(profile).success(function (data) {
            console.log(data);
            $scope.my = data;
            $location.path('/profile/'+data.userInfo.id);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };
});
