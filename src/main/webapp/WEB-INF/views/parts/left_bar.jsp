<div class="left_bar">
    <div class="logo">
        <span id="logotext">Social</span>
        <hr>
    </div>
    <div ng-show="signInInfo.isLogin()">
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
                    <a href="/logout">
                        <span class="logout-icon"></span>
                    </a>
                </li>
            </ul>
            <div class="seperate-vertical my-seperate-vertical"></div>
        </div>
        <div class="menu">
            <ul class="menu-list">
                <li class="menu-list-item active">My feed</li>
                <li class="menu-list-item">My messages</li>
                <li class="menu-list-item">Bookmarks</li>
                <li class="menu-list-item">Search people</li>
            </ul>
        </div>
    </div>
</div>