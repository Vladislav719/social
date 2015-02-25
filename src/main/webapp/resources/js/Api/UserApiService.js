/**
 * Created by ElessarST on 14.02.2015.
 */
app.service('UserApiService', function($http, ApiHelpService){
    return {
        getCurrentUser:function() {
            return $http.get('/session');
        },
        loginUser: function(param){
            return $http.post('/j_spring_security_check', $.param(param),
                {headers: {'Content-Type': 'application/x-www-form-urlencoded'}});
        },
        getUserInfo: function(userId, includePhotos){
            if (includePhotos == undefined)
                includePhotos = true;
            return $http.get('/profile/userInfo/' + userId, {params:{includePhotos: includePhotos}});
        },

        updateUserInfo: function(userInfo){
            delete userInfo.id;
            return $http.put('/profile/userInfo', userInfo);
        },
        register: function(user){
            return $http.post('/register_user', user);
        },
        addToFriend: function(friendId){
            return $http.post('/friends/requests/add/' + friendId, {});
        },
        acceptFriendRequest: function(friendId){
            return $http.post('/friends/requests/accept/' + friendId, {});
        },
        declineFriendRequest: function(friendId){
            return $http.post('/friends/requests/decline/' + friendId, {});
        },
        getInFriendRequests: function(){
            return $http.post('/friends/requests/in',{});
        },
        getOutFriendRequests: function(){
            return $http.post('/friends/requests/out',{});
        },
        cancelFriendRequest: function(friendId){
            return $http.delete('/friends/requests/remove/' + friendId)
        },
        getAllFriends: function(friendId){
            return $http.post('/friends/all/' + friendId, {})
        },
        getAllPeople: function(){
            return $http.get('/people');
        }
    }
});