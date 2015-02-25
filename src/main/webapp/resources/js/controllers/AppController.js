app.controller('AppController', ['$scope', '$http',  '$location', 'SignInInfo','UserApiService', 'UserInfo', '$timeout',
    function ($scope, $http, $location, SignInInfo, UserApiService, UserInfo, $timeout) {
        $scope.pages = {};
        $scope.pages.currentPage = '';
        $scope.mes = {};
        $scope.allFriends = [];
        $scope.go = function(path, friend){
            $location.path(path + friend.userInfo.id);
        };
        
        $scope.$on('loginError', function(){
            SignInInfo.logout();
        });


        var lastMessage = null;
        function updateMessages(){
            if (!SignInInfo.isLogin())
                return;
            var message = lastMessage == null ? '' : '?messageId=' + lastMessage;
            $http.get('/updates/messages' + message, {})
                .success(function (data) {
                    console.log(data);
                    $scope.mes.newMessages += data.length;
                    $scope.$broadcast('newMessages', data);
                    if (data != null && data.length > 0)
                        lastMessage = data[data.length - 1].messageId;

                    updateMessages();
                }).error(function(data){
                    updateMessages();
                    if (data.loginError)
                        $scope.$emit('loginError');
                });
        }
        if (SignInInfo.isLogin())
            updateMessages();


        $scope.signInInfo = SignInInfo;
        $scope.my = SignInInfo.getUserInfo();

        var updateUserInfo = function(){
            $scope.my = UserInfo.getUserInfo();
            $scope.$broadcast('userInfoUpdate');
            UserApiService.getAllFriends(SignInInfo.getUser().userId).success(function (data) {
                $scope.allFriends = data;
                $scope.$broadcast('friendsUpdate');
            }).error(function (data) {
                if (data.loginError)
                    $scope.$emit('loginError');
            });
        };

        var updateIncomeFriendRequests = function(){
            $scope.incomeFriendRequests = UserInfo.getIncomeFriendRequests();
            $scope.$broadcast('incomeFriendUpdate');
        };

        var updateOutcomeFriendRequests = function(){
            $scope.outcomeFriendRequests = UserInfo.getOutcomeFriendRequests();
            $scope.$broadcast('outcomeFriendUpdate');
        };

        UserInfo.registerIncomeCallback(updateIncomeFriendRequests);
        UserInfo.registerOutcomeCallback(updateOutcomeFriendRequests);
        UserInfo.registerUserInfoCallback(updateUserInfo);

        $scope.logout = function(){
            SignInInfo.logout();
        }
    }]);
