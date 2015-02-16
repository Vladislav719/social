/**
 * Created by ElessarST on 14.02.2015.
 */
app.service('SignInInfo', function($http, $location, UserApiService, UserInfo){
    var user = null;

    return {
        updateUser: function(){
            var self = this;
            UserApiService.getCurrentUser().success(function(data){
                self.setUser(data);
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
            if (user == null && localStorage.getItem('social') != null)
                user = JSON.parse(localStorage.getItem("social"));
            return !(user == null)
        },
        setUser: function(data){
            user = data;
            localStorage.setItem('social', JSON.stringify(data));
            UserInfo.updateAll();
        },
        login: function(userInfo){
            var self = this;
            var params = {j_username: userInfo.email, j_password: userInfo.password, 'remember-me': 'on'};
            UserApiService.loginUser(params)
                .success(function(data){
                    UserApiService.getCurrentUser().success(function(data) {
                        self.setUser(data);
                        $location.path('/profile/' + self.getUser().userId);
                    });
                }).error(function(data){
                    $location.path('/login');
                })
        },
        logout: function(){
            localStorage.removeItem('social');
            user = null;
            $location.path('/login');
        }
    }
});
