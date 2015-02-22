/**
 * Created by ElessarST on 14.02.2015.
 */

app.controller('ProfileController', function ($scope, $routeParams, $http, SignInInfo,
                                              UserApiService, WallApiService, FileUploader, LikesApi) {
    var profileId;
    $scope.uploader = new FileUploader();
    $scope.uploader.url = '/upload';
    $scope.uploader.onSuccessItem = function (fileItem, response) {
        $scope.uploader.clearQueue();
        $scope.profilePhoto = response;
        $scope.page.photo.push(response);
    };

    $scope.photoUploader = new FileUploader();
    $scope.photoUploader.url = '/add/photos';
    $scope.photoUploader.onSuccessItem = function (fileItem, response) {
        $scope.photoUploader.clearQueue();
        $scope.page.photo.push(response);
    };

    $scope.updatePhoto = function () {
        $scope.uploader.uploadAll();
    };

    $scope.addPhoto = function () {
        $scope.photoUploader.uploadAll();
    };

    $scope.enableEdit = false;
    function cloneProfile(first, second) {
        first.city = second.city;
        first.birthDate = second.birthDate;
        first.phoneNumber = second.phoneNumber;
        first.aboutMe = second.aboutMe;
        first.firstName = second.firstName;
        first.lastName = second.lastName;
    }

    function initialize() {
        profileId = $routeParams.profileId;
        $scope.profileId = profileId;
        $scope.userInfo = {};
        $scope.myProfile = profileId == SignInInfo.getUser().userId;
        $scope.currentUser = SignInInfo.getUser();
        $scope.incomeFriendRequests = [];
        $scope.outcomeFriendRequests = [];
        $scope.friends = [];
        $scope.page = {
            index: 0,
            isActive: function (index) {
                return this.index === index;
            },
            photos: [],
            decPhoto: function () {
                this.index = (this.index > 0) ? --this.index : this.photos.length - 1;
                this.mainImage = this.photos[this.index];
            },
            incPhoto: function () {
                this.index = (this.index < this.photos.length - 1) ? ++this.index : 0;
                this.mainImage = this.photos[this.index];
            },
            showPhoto: function (index) {
                this.index = index;
            }
        };
        $scope.newPost = {
            text: "",
            profileId: profileId
        };
        $scope.posts = [];

        UserApiService.getAllFriends(profileId).success(function (data) {
            $scope.friends = data
        }).error(function (data) {
            console.log(data);
        });
        WallApiService.getAllPost(profileId).success(function (data) {
            var userId = SignInInfo.getUser().userId;
            jQuery.each(data, function (index, post) {
                jQuery.each(post.likes, function (index, value) {
                    if (value.owner.id === userId)
                        post.liked = true;
                });
            });

            $scope.posts = data;
            console.log($scope.posts);
        }).error(function (data) {
            console.log(data);
        });
    }

    initialize();
    UserApiService.getUserInfo(profileId, true)
        .success(function (data) {
            $scope.profile = data.userInfo;
            $scope.profile.status = data.status;
            console.log(data);
            $scope.profilePhoto = data.photo;
            $scope.page.mainImage = $scope.profilePhoto;
            $scope.page.photos =data.photos;
            $scope.profileHistory = {};
            cloneProfile($scope.profileHistory, $scope.profile);
        });

    $scope.cancelUpdateUserInfo = function () {
        cloneProfile($scope.profile, $scope.profileHistory);
        $scope.enableEdit = false;
    };

    $scope.addToFriend = function () {
        UserApiService.addToFriend(profileId);
    };


    $scope.updateUserInfo = function () {
        var profile = $scope.profile;
        delete profile.user;
        UserApiService.updateUserInfo(profile).success(function (data) {
            console.log(data);
            $scope.profile = data;
            cloneProfile($scope.profileHistory, $scope.profile);
            $scope.enableEdit = false;
        }).error(function (data) {
            console.log(data);
        })
    };

    $scope.addPost = function () {
        WallApiService.addPost($scope.newPost).success(function (data) {
            $scope.posts.unshift(data);
            console.log(data)
        }).error(function (data) {
            console.log(data)
        });
    };

    $scope.deletePost = function (post, index) {
        WallApiService.deletePost(post.postId).success(function (data) {
            $scope.posts.remove(index);
            console.log(data)
        }).error(function (data) {
            console.log(data)
        });
    };

    $scope.editPost = function (post, index) {
        WallApiService.editPost(post.id, {text: post.text}).success(function (data) {
            $scope.posts[index] = data;
            console.log(data);
        }).error(function (data) {
            console.log(data)
        });
    };

    $scope.getPost = function (index) {
        WallApiService.getPost($scope.posts[index].id).success(function (data) {
            console.log(data)
        }).error(function (data) {
            console.log(data)
        });
    };

    $scope.toggleLike = function (post) {
        var userId = SignInInfo.getUser().userId;
        if (post.liked) {
            LikesApi.removeLikePost(post.postId).success(function (data) {
                post.liked = false;
                var ind = -1;
                $.each(post.likes, function (index, value) {
                    if (value.owner.id == userId)
                        ind = index;
                });
                post.likes.remove(ind, ind);
            });
        } else {
            LikesApi.addLikePost(post.postId).success(function (data) {
                post.likes.push({owner: {id:userId}});
                post.liked = true
            });
        }
    };


    $scope.cancelFriendRequest = function (friendId) {
        UserApiService.cancelFriendRequest($scope.outcomeFriendRequests[friendId].to.id).success(function (data) {
            console.log(data);
        }).error(function (data) {
            console.log(data);
        })
    };

    $scope.removeFromFriends = function (friendId) {
        UserApiService.cancelFriendRequest($scope.friends[friendId].id).success(function (data) {
            console.log(data);
        }).error(function (data) {
            console.log(data);
        })
    };

});

