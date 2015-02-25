/**
 * Created by ElessarST on 21.02.2015.
 */
app.controller('ChatController', function($scope, $http, SignInInfo, MessagesApi,
                                          UserApiService, $routeParams, $timeout, $location){
    $scope.pages.currentPage = 'messages';
    var chatId = $routeParams.id;

    if (chatId == SignInInfo.getUser().userId)
        $location.path('/messages/');

    $scope.$on('newMessages', function(event,data){
        for (var i = 0; i < data.length; i++){
            if (data[i].from.id == chatId){
                $scope.messages.push(data[i]);
                scrollBottom();
            }
        }
    });

    function markRead(){
        MessagesApi.readMessages(chatId).success(function(){
            for (var i = $scope.messages.length - 1; i >= 0; i--){
                if ($scope.messages[i].to.id != $scope.my.userInfo.id)
                    continue;
                if ($scope.messages[i].read === true )
                    break;
                $scope.messages[i].read = true;
                $scope.mes.newMessages--;
                $scope.$apply();
            }
        }).error(function (data) {
            if (data.loginError)
                $scope.$emit('loginError');
        });
    }

    $scope.chatId = chatId;
    $scope.messages = [];
    $scope.newMessage = '';
    function scrollBottom(){
        $timeout(function(){
            jQuery('.messages-wrap').scrollTop(jQuery('.scroller').height());
        }, 5);
    }

    MessagesApi.getMessages(chatId).success(function(data){
        $scope.messages = data;
        scrollBottom();
        $timeout(markRead, 2000);
    }).error(function (data) {
        if (data.loginError)
            $scope.$emit('loginError');
    });

    UserApiService.getUserInfo(chatId, false)
        .success(function (data) {
            $scope.companion = data;
        }).error(function (data) {
            if (data.loginError){
                $scope.$emit('loginError');
                return;
            }
            $location.path('/messages/');
        });


    $scope.sendMessage = function(){
       MessagesApi.sendMessage(chatId, $scope.newMessage)
           .success(function (data) {
               $scope.messages.push(data);
               scrollBottom();
               $scope.newMessage = ''
           }).error(function (data) {
               if (data.loginError)
                   $scope.$emit('loginError');
           });
    }
});
