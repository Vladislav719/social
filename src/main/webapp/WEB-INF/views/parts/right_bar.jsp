<div class="right_bar">
    <div ng-show="signInInfo.isLogin()" style="height: 100%">
        <h1>Your friends</h1>

        <div class="search">
            <input type="text" class="search_inp" placeholder="Friend name"/>
        </div>
        <div class="friends-block-wrapper">
            <div class="friends-block">
                <div class="friend" ng-repeat="friend in allFriends">
                    <div class="friend-profile-image profile-image">

                        <img src="{{friend.photo.photoPath}}" ng-click="go('/profile/', friend)"/>
                    </div>
                    <ul class="icons friend-icons">
                        <li class="icons-item-block text-left">
                            <span ng-click="go('/messages/', friend)">
								<span class="email-icon">
									<span class="icon-text">messages</span>
								</span>
                            </span>
                        </li>
                        <li class="icons-item-block fix-user-icon text-left">
								<span class="user-icon " ng-click="go('/profile/', friend)">
									<span class="icon-text">profile</span>
								</span>
                        </li>
                    </ul>

                    <span class="friend-name">
                        <span ng-click="go('/profile/', friend)">
                            {{friend.userInfo.firstName + " " + friend.userInfo.lastName}}
                        </span>
                    </span>

                    <div class="seperate-vertical friend-seperate-vertical"></div>
                </div>
            </div>
        </div>
    </div>
</div>