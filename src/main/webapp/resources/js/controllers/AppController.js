app.controller('AppController', ['$scope', '$http', 'SignInInfo', function($scope, $http, SignInInfo){
    $scope.signInInfo = SignInInfo;
}]);
