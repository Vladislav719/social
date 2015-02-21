/**
 * Created by ElessarST on 21.02.2015.
 */
app.controller('ChatController', function($scope, $http, SignInInfo, MessagesApi, UserApiService, $routeParams){
    var chatId = $routeParams.id;
    $scope.chatId = chatId;
    $scope.messages = [];
    $scope.newMessage = '';
    MessagesApi.getMessages(chatId).success(function(data){
        $scope.messages = data;
    });

    UserApiService.getUserInfo(chatId, false)
        .success(function (data) {
            $scope.companion = data;
        });

    $scope.sendMessage = function(){
       MessagesApi.sendMessage(chatId, $scope.newMessage)
           .success(function (data) {
               $scope.messages.push(data);
               $scope.newMessage = ''
           })
    }
});
