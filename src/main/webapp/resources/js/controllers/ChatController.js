/**
 * Created by ElessarST on 21.02.2015.
 */
app.controller('ChatController', function($scope, $http, SignInInfo, MessagesApi,
                                          UserApiService, $routeParams, $timeout){
    var chatId = $routeParams.id;

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
        })
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
    });

    UserApiService.getUserInfo(chatId, false)
        .success(function (data) {
            $scope.companion = data;
        });


    $scope.sendMessage = function(){
       MessagesApi.sendMessage(chatId, $scope.newMessage)
           .success(function (data) {
               $scope.messages.push(data);
               scrollBottom();
               $scope.newMessage = ''
           })
    }
});