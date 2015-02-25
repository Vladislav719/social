/**
 * Created by ElessarST on 25.02.2015.
 */
app.controller("PhotosController", function($scope, SignInInfo, UserApiService,
                                            $routeParams, FileUploader){
    var profileId = $routeParams.id;
    $scope.profileId = profileId;
    $scope.userId = SignInInfo.getUser().userId;
    $scope.photos = [];

    $scope.page = {currentPhoto: {}};
    UserApiService.getUserInfo(profileId, true)
        .success(function (data) {
            $scope.photos = data.photos;
            $scope.page.currentPhoto = data.photos[0];
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });

    $scope.photoUploader = new FileUploader();
    $scope.photoUploader.url = '/add/photos';
    $scope.photoUploader.onSuccessItem = function (fileItem, response) {
        $scope.photoUploader.clearQueue();
        $scope.photos.push(response);
    };

    $scope.addPhoto = function () {
        $scope.photoUploader.uploadAll();
    };
});
