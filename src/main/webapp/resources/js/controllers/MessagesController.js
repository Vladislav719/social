/**
 * Created by ElessarST on 21.02.2015.
 */
app.controller('MessageController', function($scope, $http, SignInInfo, MessagesApi, UserApiService){
    $scope.pages.currentPage = 'messages';
    MessagesApi.getDialogs().success(function (data) {
        $scope.dialogs = data;
    }).error(function (data) {
        if (data.loginError)
            $scope.$emit('loginError');
    });;
});
