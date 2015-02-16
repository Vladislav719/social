app.controller('AppController', ['$scope', '$http',  '$location', 'SignInInfo','UserApiService', 'UserInfo', '$timeout',
    function ($scope, $http, $location, SignInInfo, UserApiService, UserInfo, $timeout) {

        $scope.signInInfo = SignInInfo;

        var updateIncomeFriendRequests = function(){
            $scope.incomeFriendRequests = UserInfo.getIncomeFriendRequests()
        };

        var updateOutcomeFriendRequests = function(){
            $scope.outcomeFriendRequests = UserInfo.getOutcomeFriendRequests()
        };

        var updateCurrentNotification = function(){
            $scope.currentNotification = UserInfo.getCurrentNotification().from;
            $scope.currentNotificationId = UserInfo.getCurrentNotificationId().from;
        };

        UserInfo.registerIncomeCallback(updateIncomeFriendRequests);
        UserInfo.registerOutcomeCallback(updateOutcomeFriendRequests);
        UserInfo.registerNotificationIdCallback(updateCurrentNotification);


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
