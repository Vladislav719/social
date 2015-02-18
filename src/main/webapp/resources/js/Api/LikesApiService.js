/**
 * Created by ElessarST on 19.02.2015.
 */
app.service('LikesApi', function($http, ApiHelpService) {
    return {
        addLikePost: function(postId){
            return $http.post('/likes/post/' + postId, {});
        },
        removeLikePost: function(postId){
            return $http.delete('/likes/post/' + postId, {});
        },
        addLikePhoto: function(photoId){
            return $http.post('/likes/photo/' + photoId, {});
        },
        removeLikePhoto: function(photoId){
            return $http.delete('/likes/photo/' + photoId, {});
        }
    }
});