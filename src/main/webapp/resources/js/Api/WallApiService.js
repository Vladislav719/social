/**
 * Created by ElessarST on 15.02.2015.
 */
app.service('WallApiService', function($http, ApiHelpService){
    return {
        addPost: function(param){
            return $http.post('/walls/add', param);
        },
        deletePost: function(postId){
            return $http.delete('/walls/remove/' + postId, {});
        },
        editPost: function(postId, param){
            return $http.put('/walls/edit/' + postId, param);
        },
        getPost: function(postId){
            return $http.get('/walls/get/' + postId, {});
        },
        getAllPost: function(profileId){
            return $http.get('/walls/get/all/' + profileId, {});
        }
    }
});

