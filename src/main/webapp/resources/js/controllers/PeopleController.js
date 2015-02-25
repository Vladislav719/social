/**
 * Created by ElessarST on 24.02.2015.
 */
app.controller('PeopleController', function($scope, SignInInfo, UserApiService, UserInfo){
    $scope.pages.currentPage = 'people';
    UserApiService.getAllPeople()
        .success(function (data) {
            $scope.peoples = data;
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });;

    $scope.addToFriend = function (people) {
        UserApiService.addToFriend(people.user.id).success(function (data) {
            people.status = 1;
            UserInfo.updateAll(SignInInfo.getUser().userId);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });;
    };

    $scope.cancelFriendRequest = function (people) {
        UserApiService.cancelFriendRequest(people.user.id).success(function (data) {
            people.status = null;
            UserInfo.updateAll(SignInInfo.getUser().userId);

        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };

    $scope.acceptFriendRequest =function(people){
        UserApiService.acceptFriendRequest(people.user.id).success(function (data) {
            people.status = 1;
            UserInfo.updateAll(SignInInfo.getUser().userId);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });;
    };

});
