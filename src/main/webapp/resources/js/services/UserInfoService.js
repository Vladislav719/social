/**
 * Created by ElessarST on 15.02.2015.
 */


app.service('UserInfo', function ($http, $location, UserApiService) {
    var observerIncomeCallbacks = [];
    var observerOutcomeCallbacks = [];
    var observerNotificationIdCallbacks = [];
    var notifyObservers = function (observers) {
        angular.forEach(observers, function (callback) {
            callback();
        });
    };
    var incomeFriendRequests = [];
    var outcomeFriendRequests = [];
    var currentNotificationId = 0;
    var currentNotification = null;
    return {
        getIncomeFriendRequests: function(){
            return incomeFriendRequests;
        },
        getOutcomeFriendRequests: function(){
            return outcomeFriendRequests;
        },
        getCurrentNotificationId: function(){
            return currentNotificationId;
        },
        getCurrentNotification: function(){
            if (currentNotification === null)
                return {from: {}};
            return currentNotification;
        },
        registerIncomeCallback: function (callback) {
            observerIncomeCallbacks.push(callback);
        },
        registerOutcomeCallback: function (callback) {
            observerOutcomeCallbacks.push(callback);
        },
        registerNotificationIdCallback: function (callback) {
            observerNotificationIdCallbacks.push(callback);
        },
        updateOutFriendRequest: function() {
            UserApiService.getOutFriendRequests().success(function (data) {
                outcomeFriendRequests = data;
                notifyObservers(observerOutcomeCallbacks);
            }).error(function (data) {
                console.log(data);
            });
        },
        updateInFriendRequests: function () {
            UserApiService.getInFriendRequests().success(function (data) {
                incomeFriendRequests = data;
                currentNotificationId = 0;
                currentNotification = data[0];
                notifyObservers(observerIncomeCallbacks);
                notifyObservers(observerNotificationIdCallbacks);
            }).error(function (data) {
                console.log(data);
            });
        },
        updateAll: function () {
            this.updateOutFriendRequest();
            this.updateInFriendRequests();
        },
        getCurrentNotification: function () {
            return currentNotification;
        },
        nextFriendRequest: function () {
            currentNotificationId = (currentNotificationId + 1) % incomeFriendRequests.length;
            currentNotification = incomeFriendRequests[currentNotificationId];
            notifyObservers(observerNotificationIdCallbacks);
        },
        prevFriendRequest: function () {
            currentNotificationId = (currentNotificationId - 1);
            if (currentNotificationId < 0)
                currentNotificationId += incomeFriendRequests.length;
            currentNotification = incomeFriendRequests[currentNotificationId];
            notifyObservers(observerNotificationIdCallbacks);
        },

        acceptFriendRequest: function (friendId) {
            UserApiService.acceptFriendRequest(incomeFriendRequests[friendId].from.user.userId).success(function (data) {
                console.log(data)
            }).error(function (data) {
                console.log(data);
            })
        },

        declineFriendRequest: function (friendId) {
            UserApiService.declineFriendRequest(incomeFriendRequests[friendId].from.user.userId).success(function (data) {
                console.log(data)
            }).error(function (data) {
                console.log(data);
            })
        }

    }
})
;