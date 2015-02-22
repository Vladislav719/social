/**
 * Created by ElessarST on 21.02.2015.
 */
app.controller('MessageController', function($scope, $http, SignInInfo, MessagesApi, UserApiService){
    MessagesApi.getDialogs().success(function (data) {
        $scope.dialogs = data;
    });
});
