/**
 * Created by ElessarST on 14.02.2015.
 */

app.controller('ProfileController', function ($scope, $routeParams, $http, SignInInfo,
                                              UserApiService, WallApiService, FileUploader, LikesApi) {
    $scope.pages.currentPage = 'profile';

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
        $scope.enableEdit = false;
        profileId = $routeParams.profileId;
        $scope.profileId = profileId;
        $scope.userInfo = {};
        $scope.myProfile = profileId == SignInInfo.getUser().userId;
        $scope.currentUser = SignInInfo.getUser();
        $scope.incomeFriendRequests = [];
        $scope.outcomeFriendRequests = [];
        $scope.friends = [];
        $scope.newPost = {
            text: "",
            profileId: profileId
        };
        $scope.posts = [];

        UserApiService.getAllFriends(profileId).success(function (data) {
            $scope.friends = data
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
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
            if (data.loginError)
                $scope.$emit('loginError');
        });
    }

    initialize();
    UserApiService.getUserInfo(profileId, false)
        .success(function (data) {
            $scope.profile = data.userInfo;
            $scope.profile.status = data.status;
            console.log(data);
            $scope.profilePhoto = data.photo;
            $scope.profileHistory = {};
            cloneProfile($scope.profileHistory, $scope.profile);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });;

    $scope.cancelUpdateUserInfo = function () {
        cloneProfile($scope.profile, $scope.profileHistory);
        $scope.enableEdit = false;
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
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };

    $scope.addPost = function () {
        WallApiService.addPost($scope.newPost).success(function (data) {
            $scope.posts.unshift(data);
            $scope.newPost.text = '';
            console.log(data)
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };

    $scope.deletePost = function (post, index) {
        WallApiService.deletePost(post.postId).success(function (data) {
            $scope.posts.remove(index);
            console.log(data)
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };

    $scope.editPost = function (post, index) {
        WallApiService.editPost(post.postId, {text: post.text}).success(function (data) {
            $scope.posts[index] = data;
            console.log(data);
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    };

    $scope.getPost = function (index) {
        WallApiService.getPost($scope.posts[index].id).success(function (data) {
            console.log(data)
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
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
            }).error(function (data) {
                if (data.loginError)
                    $scope.$emit('loginError');
            });
        } else {
            LikesApi.addLikePost(post.postId).success(function (data) {
                if (post.likes == null || post.likes == undefined)
                    post.likes = [];
                post.likes.push({owner: {id:userId}});
                post.liked = true
            }).error(function (data) {
                if (data.loginError)
                    $scope.$emit('loginError');
            });
        }
    };




});

