/**
 * Created by ElessarST on 14.02.2015.
 */
app.service('SignInInfo', function($http, $location, UserApiService, UserInfo){
    var user = null;
    var userInfo = null;
    return {
        getUserInfo: function(){
            var self = this;
            if (userInfo == null && user != null)
                UserApiService.getUserInfo(self.getUser().userId).success(function (data) {
                    userInfo = data;
                });
            return userInfo
        },
        updateUser: function(){
            var self = this;
            UserApiService.getCurrentUser().success(function(data){
                self.setUser(data);
                UserApiService.getUserInfo(self.getUser().userId, false)
                    .success(function (data) {
                        userInfo = data;
                    });
            }).error(function(data){
                user = null;
            });
        },
        checkRoute: function(route){
            var cancel;
            if (this.isLogin()){
                cancel = 'notAuth';
            } else{
                cancel = 'auth';
            }
            return !(route.name == cancel)
        },
        redirect: function(){
            if (this.isLogin())
                return '/profile/' + user.userId;
            else
                return '/login';
        },
        getUser: function(){
            return user;
        },
        isLogin: function(){
            if (user == null && localStorage.getItem('social') != null){
                this.setUser(JSON.parse(localStorage.getItem("social")));
            }
            return !(user == null)
        },
        setUser: function(data){
            user = data;
            userInfo = null;
            localStorage.setItem('social', JSON.stringify(data));
            UserInfo.updateAll(this.getUser().userId);
        },
        login: function(userInfo){
            var self = this;
            var params = {j_username: userInfo.email, j_password: userInfo.password, 'remember-me': 'on'};
            return UserApiService.loginUser(params);

        },
        logout: function(){
            localStorage.removeItem('social');
            user = null;
            $location.path('/login');
        }
    }
});
