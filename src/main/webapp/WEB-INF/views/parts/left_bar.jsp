<div class="left_bar">
    <div class="logo">
        <span id="logotext">Social</span>
        <hr>
    </div>
    <div ng-show="signInInfo.isLogin()" style="height: 100%">
        <div class="my-profile-block">
            <div class="profile-image my-profile-image">
                <img src="https://pp.vk.me/c306815/v306815704/80c1/imTMKnFgKc4.jpg">
            </div>
            <ul class="icons my-icons">
                <li class="icons-item">
                    <span class="photo-icon"></span>
                </li>
                <li class="icons-item">
                    <span class="tools-icon"></span>
                </li>
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
                <li class="menu-list-item">My messages</li>
                <li class="menu-list-item">Search people</li>
            </ul>
        </div>
        
        <div class="notifications-block-wrapper">
            <h1>Notifications</h1>
            <div class="notifications-block">
                <div class="notification">
                    <div class="notification-count">
                        {{(currentNotificationId + 1) + ' / ' + incomeFriendRequests.length}}
                    </div>
                    <div class="notification-type">Friend requests</div>
                    <div class="notification-user">{{currentNotification.firstName + " " + currentNotification.lastName}}</div>
                    <i class="glyphicon glyphicon-ok hover-white hover-cursor" ng-click="acceptFriendRequest(currentNotificationId)"></i>
                    <i class="glyphicon glyphicon-remove hover-white hover-cursor" ng-click="declineFriendRequest(currentNotificationId)"></i>
                    <i class="glyphicon glyphicon glyphicon-chevron-left hover-white hover-cursor" ng-click="prevFriendRequest()"></i>
                    <i class="glyphicon glyphicon glyphicon-chevron-right hover-white hover-cursor" ng-click="nextFriendRequest()"></i>
                </div>
                <div class="notification">
                    <div class="notification-type">Last unread Messages</div>
                    <div class="notification-user">Author Notification</div>
                    <div class="text-message">ajsdflkasdjf;aksjdf;</div>
                    <i class="glyphicon glyphicon-ok hover-white hover-cursor"></i>
                    <i class="glyphicon glyphicon-remove hover-white hover-cursor"></i>
                    <i class="glyphicon glyphicon glyphicon-chevron-left hover-white hover-cursor"></i>
                    <i class="glyphicon glyphicon glyphicon-chevron-right hover-white hover-cursor"></i>
                </div>
            </div>
        </div>

    </div>
</div>