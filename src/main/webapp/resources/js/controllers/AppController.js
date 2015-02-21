app.controller('AppController', ['$scope', '$http',  '$location', 'SignInInfo','UserApiService', 'UserInfo', '$timeout',
    function ($scope, $http, $location, SignInInfo, UserApiService, UserInfo, $timeout) {


        $scope.signInInfo = SignInInfo;
        $scope.my = SignInInfo.getUserInfo();

        var updateUserInfo = function(){
            $scope.my = UserInfo.getUserInfo();
        };

        var updateIncomeFriendRequests = function(){
            $scope.incomeFriendRequests = UserInfo.getIncomeFriendRequests()
        };

        var updateOutcomeFriendRequests = function(){
            $scope.outcomeFriendRequests = UserInfo.getOutcomeFriendRequests()
        };

        var updateCurrentNotification = function(){
            $scope.currentNotification = UserInfo.getCurrentNotification().from;
            $scope.currentNotificationId = UserInfo.getCurrentNotificationId();
        };

        UserInfo.registerIncomeCallback(updateIncomeFriendRequests);
        UserInfo.registerOutcomeCallback(updateOutcomeFriendRequests);
        UserInfo.registerNotificationIdCallback(updateCurrentNotification);
        UserInfo.registerUserInfoCallback(updateUserInfo);


        $scope.declineFriendRequest = function(notificationId){
            UserInfo.declineFriendRequest(notificationId);
        };

        $scope.nextFriendRequest = function(){
            UserInfo.nextFriendRequest();
        };

        $scope.prevFriendRequest = function(){
            UserInfo.prevFriendRequest();
        };

        $scope.logout = function(){
            SignInInfo.logout();
        }
    }]);
