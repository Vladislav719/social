/**
 * Created by ElessarST on 14.02.2015.
 */

app.controller('ProfileController', function($scope, $routeParams, $http, SignInInfo, UserApiService){
    var profileId;
    function initialize(){
        profileId = $routeParams.profileId;
        $scope.profileId = profileId;
        $scope.userInfo = {};
        $scope.incomeFriendRequests = [];
        $scope.outcomeFriendRequests = [];
        $scope.friends = [];
        $scope.myProfile = !!(SignInInfo.isLogin() && profileId == SignInInfo.getUser().userId);
        if ($scope.myProfile){
            UserApiService.getInFriendRequests().success(function(data){
                $scope.incomeFriendRequests = data
            }).error(function(data){
                console.log(data);
            });
            UserApiService.getOutFriendRequests().success(function(data){
                $scope.outcomeFriendRequests = data
            }).error(function(data){
                console.log(data);
            });
        }
        UserApiService.getAllFriends(profileId).success(function(data){
            $scope.friends = data
        }).error(function(data){
            console.log(data);
        });
    }
    initialize();
    UserApiService.getUserInfo(profileId)
        .success(function(data){
            $scope.userInfo = data;
        });

    $scope.addToFriend = function(){
        UserApiService.addToFriend(profileId);
    };

    $scope.acceptFriendRequest = function(friendId){
        UserApiService.acceptFriendRequest($scope.incomeFriendRequests[friendId].from.user.userId).success(function(data){
            console.log(data)
        }).error(function(data){
            console.log(data);
        })
    };

    $scope.declineFriendRequest = function(friendId){
        UserApiService.declineFriendRequest($scope.incomeFriendRequests[friendId].from.user.userId).success(function(data){
            console.log(data)
        }).error(function(data){
            console.log(data);
        })
    };

    $scope.cancelFriendRequest = function(friendId){
        UserApiService.cancelFriendRequest($scope.outcomeFriendRequests[friendId].to.user.userId).success(function(data){
            console.log(data);
        }).error(function(data){
            console.log(data);
        })
    };

    $scope.removeFromFriends = function(friendId){
        UserApiService.cancelFriendRequest($scope.friends[friendId].user.userId).success(function(data){
            console.log(data);
        }).error(function(data){
            console.log(data);
        })
    };

    $scope.updateUserInfo = function(){
        UserApiService.updateUserInfo(profileId, $scope.userInfo).success(function(data){
            console.log(data);
            $scope.userInfo = data;
        }).error(function(data){
            console.log(data);
        })
    }
});

