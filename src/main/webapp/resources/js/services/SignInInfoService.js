/**
 * Created by ElessarST on 14.02.2015.
 */
app.service('SignInInfo', function($http, $location, UserApiService){
    var user = null;
    return {
        updateUser: function(){
            UserApiService.getCurrentUser().success(function(data){
                user = data;
            }).error(function(data){
                user = null;
            });
        },
        getUser: function(){
            return user;
        },
        isLogin: function(){
            return !(user == null)
        },
        setUser: function(data){
            user = data;
        },
        login: function(userInfo){
            var params = {j_username: userInfo.email, j_password: userInfo.password, 'remember-me': 'on'};
            UserApiService.loginUser(params)
                .success(function(data){
                    $location.path('/profile/' + user.userId);
                }).error(function(data){
                    $location.path('/login');
                })
        },
        logout: function(){
            localStorage.removeItem('social');
            user = null;
            $location.path('/');
        }
    }
});
