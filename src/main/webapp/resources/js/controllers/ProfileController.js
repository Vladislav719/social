/**
 * Created by ElessarST on 14.02.2015.
 */

app.controller('ProfileController', function($scope, $routeParams, $http, SignInInfo, UserApiService, WallApiService){
    var profileId;
    function initialize(){
        profileId = $routeParams.profileId;
        $scope.profileId = profileId;
        $scope.userInfo = {};
        $scope.incomeFriendRequests = [];
        $scope.outcomeFriendRequests = [];
        $scope.friends = [];
        $scope.newPost = {
            text: "",
            profileId: profileId
        };
        $scope.posts = [];

        UserApiService.getAllFriends(profileId).success(function(data){
            $scope.friends = data
        }).error(function(data){
            console.log(data);
        });
        WallApiService.getAllPost(profileId).success(function(data){
            $scope.posts = data
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


    $scope.updateUserInfo = function(){
        UserApiService.updateUserInfo(profileId, $scope.userInfo).success(function(data){
            console.log(data);
            $scope.userInfo = data;
        }).error(function(data){
            console.log(data);
        })
    };

    $scope.addPost = function(){
        WallApiService.addPost($scope.newPost).success(function(data){
            console.log(data)
        }).error(function(data){
            console.log(data)
        });
    };

    $scope.deletePost = function(index){
        WallApiService.deletePost($scope.posts[index].id).success(function(data){
            console.log(data)
        }).error(function(data){
            console.log(data)
        });
    };

    $scope.editPost = function(index){
        WallApiService.editPost($scope.posts[index].id, {text: $scope.posts[index].text}).success(function(data){
            console.log(data)
        }).error(function(data){
            console.log(data)
        });
    };

    $scope.getPost = function(index){
        WallApiService.getPost($scope.posts[index].id).success(function(data){
            console.log(data)
        }).error(function(data){
            console.log(data)
        });
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

});

