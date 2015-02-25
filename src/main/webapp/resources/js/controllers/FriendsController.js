/**
 * Created by ElessarST on 25.02.2015.
 */
app.controller('FriendsController', function(SignInInfo, $scope, $http, UserApiService, UserInfo){
    $scope.peoples = $scope.allFriends;
    $scope.pages.currentPage = 'friends';

    UserInfo.updateAll(SignInInfo.getUser().userId);

    $scope.friends = {
        page: 'friends'
    };
    $scope.$on('friendsUpdate', function(){
        $scope.peoples = $scope.allFriends;
    });

    $scope.$on('outcomeFriendUpdate', function(){
        $scope.outcome = $scope.outcomeFriendRequests;
    });

    $scope.$on('incomeFriendUpdate', function(){
        $scope.income = $scope.incomeFriendRequests;
    });

    $scope.addToFriend = function (people) {
        UserApiService.addToFriend(people.userInfo.id).success(function (data) {
            UserInfo.updateAll(SignInInfo.getUser().userId);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });;
    };

    $scope.acceptFriendRequest =function(people){
        UserApiService.acceptFriendRequest(people.userInfo.id).success(function (data) {
            UserInfo.updateAll(SignInInfo.getUser().userId);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });;
    };

    $scope.cancelFriendRequest = function (people) {
        UserApiService.cancelFriendRequest(people.userInfo.id).success(function (data) {
            UserInfo.updateAll(SignInInfo.getUser().userId);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };

    $scope.removeFromFriends = function (people) {
        UserApiService.cancelFriendRequest(people.userInfo.id).success(function (data) {
            UserInfo.updateAll(SignInInfo.getUser().userId);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };

});
