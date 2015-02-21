/**
 * Created by ElessarST on 21.02.2015.
 */

app.service('MessagesApi', function( $http){
    return {
        getDialogs: function(){
            return $http.get('/messages');
        },
        sendMessage: function(dialogId, message){
            return $http.post('/messages/' + dialogId, {text:message} );
        },
        getMessages: function(dialogId){
            return $http.get('/messages/' + dialogId);
        }
    }
});