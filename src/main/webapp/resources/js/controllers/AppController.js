app.controller('AppController', ['$scope', '$http',  '$location', 'SignInInfo','UserApiService', 'UserInfo', '$timeout',
    function ($scope, $http, $location, SignInInfo, UserApiService, UserInfo, $timeout) {
        $scope.mes = {};
        $scope.allFriends = [];

        $scope.go = function(path, friend){
            $location.path(path + friend.userInfo.id);
        };
        var lastMessage = null;
        function updateMessages(){
            var message = lastMessage == null ? '' : '?messageId=' + lastMessage;
            $http.get('/updates/messages' + message, {})
                .success(function (data) {
                    console.log(data);
                    $scope.mes.newMessages += data.length;
                    $scope.$broadcast('newMessages', data);
                    if (data != null && data.length > 0)
                        lastMessage = data[data.length - 1].messageId;
                    updateMessages();
                });
        }
        updateMessages();


        $scope.signInInfo = SignInInfo;
        $scope.my = SignInInfo.getUserInfo();

        var updateUserInfo = function(){
            $scope.my = UserInfo.getUserInfo();
            UserApiService.getAllFriends(SignInInfo.getUser().userId).success(function (data) {
                $scope.allFriends = data;
            });
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

        $scope.acceptFriendRequest = function(notificationId){
            UserInfo.acceptFriendRequest(notificationId);
            UserInfo.updateAll($scope.my.userInfo.Id);
        };

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
