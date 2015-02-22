<div class="left_bar">
    <div class="logo">
        <span id="logotext">Social</span>
        <hr>
    </div>
    <div ng-show="signInInfo.isLogin()" style="height: 100%">
        <div class="my-profile-block">
            <div class="profile-image my-profile-image">
                <img src="{{my.photo.photoPath}}" ng-click="go('/profile/', my)">
            </div>
            <ul class="icons my-icons">
                <li class="icons-item">
                    <a href="/logout" ng-click='logout()'>
                        <span class="logout-icon"></span>
                    </a>
                </li>
            </ul>
            <div class="seperate-vertical my-seperate-vertical"></div>
        </div>
        <div class="menu">
            <ul class="menu-list">
                <li class="menu-list-item">
                    <a ng-href="/#/messages" class="link">
                        My messages <span ng-show="mes.newMessages > 0">{{mes.newMessages}}</span>
                    </a>
                </li>
                <li class="menu-list-item">Search people</li>
            </ul>
        </div>
        
        <div class="notifications-block-wrapper">
            <h1>Notifications</h1>
            <div class="notifications-block">
                <div class="notification">
                    <div class="notification-count">
                        <span ng-if="notificationId > 0">
                            {{(currentNotificationId + 1)}}
                            </span>
                        <span ng-if="!(notificationId > 0)">
                            0
                            </span>/  {{incomeFriendRequests.length}}
                    </div>

                    <div class="notification-type">Friend requests</div>
                    <div ng-if="(notificationId > 0)">
                        <div class="notification-user">{{currentNotification.firstName + " " + currentNotification.lastName}}</div>
                        <i class="glyphicon glyphicon-ok hover-white hover-cursor" ng-click="acceptFriendRequest(currentNotificationId)"></i>
                        <i class="glyphicon glyphicon-remove hover-white hover-cursor" ng-click="declineFriendRequest(currentNotificationId)"></i>
                        <i class="glyphicon glyphicon glyphicon-chevron-left hover-white hover-cursor" ng-click="prevFriendRequest()"></i>
                        <i class="glyphicon glyphicon glyphicon-chevron-right hover-white hover-cursor" ng-click="nextFriendRequest()"></i>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>