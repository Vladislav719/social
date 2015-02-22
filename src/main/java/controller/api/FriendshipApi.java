package controller.api;

import model.Friendship;
import model.User;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.FriendshipService;
import service.UserLoginService;
import service.gson.GsonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ElessarST on 14.02.2015.
 */
@RestController
public class FriendshipApi {
    @Autowired
    GsonService gsonService;

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    FriendshipService friendshipService;

    @RequestMapping(value = "/friends/requests/add/{friendId}", method = RequestMethod.POST)
    public @ResponseBody Object addRequest(@PathVariable Long friendId){
        if (!userLoginService.isAuthenticated())
            return userLoginService.notAuthError();
        User currentUser = userLoginService.getCurrentUser();
        friendshipService.addRequest(currentUser.getUserId(), friendId);
        return gsonService.success("Request was successfully send");
    }

    @RequestMapping(value = "/friends/requests/decline/{friendId}", method = RequestMethod.POST)
    public @ResponseBody Object declineRequest(@PathVariable Long friendId){
        if (!userLoginService.isAuthenticated())
            return userLoginService.notAuthError();
        User currentUser = userLoginService.getCurrentUser();
        boolean request = friendshipService.declineRequest(currentUser.getUserId(), friendId);
        if (request)
            return gsonService.success("Request was successfully declined");
        else
            return gsonService.error("Something is wrong");
    }

    @RequestMapping(value = "/friends/requests/accept/{friendId}", method = RequestMethod.POST)
    public @ResponseBody Object acceptRequest(@PathVariable Long friendId){
        if (!userLoginService.isAuthenticated())
            return userLoginService.notAuthError();
        User currentUser = userLoginService.getCurrentUser();
        boolean request = friendshipService.acceptRequest(currentUser.getUserId(), friendId);
        if (request)
            return gsonService.success("Request was successfully accepted");
        else
            return gsonService.error("Something is wrong");
    }

    @RequestMapping(value = "/friends/all/{friendId}", method = RequestMethod.POST)
    public @ResponseBody Object getFriends(@PathVariable Long friendId){
        List<UserInfo> friends = friendshipService.getAllFriends(friendId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (UserInfo friend : friends){
            result.add(gsonService.includePhotos(friend, false));
        }
        return gsonService.standardBuilder().toJson(result);
    }

    @RequestMapping(value = "/friends/requests/in", method = RequestMethod.POST)
    public @ResponseBody Object getIncomingRequests(){
        if (!userLoginService.isAuthenticated())
            return userLoginService.notAuthError();
        User currentUser = userLoginService.getCurrentUser();
        List<Friendship> friendsReq = friendshipService.getAllIncomingRequests(currentUser.getUserId());
        return gsonService.standardBuilder().toJson(friendsReq);
    }

    @RequestMapping(value = "/friends/requests/out", method = RequestMethod.POST)
    public @ResponseBody Object getOutcomingRequests(){
        if (!userLoginService.isAuthenticated())
            return userLoginService.notAuthError();
        User currentUser = userLoginService.getCurrentUser();
        List<Friendship> friendsReq = friendshipService.getAllOutcomingRequests(currentUser.getUserId());
        return gsonService.standardBuilder().toJson(friendsReq);
    }

    @RequestMapping(value = "/friends/requests/remove/{friendId}", method = RequestMethod.DELETE)
    public @ResponseBody Object getOutcomingRequests(@PathVariable Long friendId){
        if (!userLoginService.isAuthenticated())
            return userLoginService.notAuthError();
        User currentUser = userLoginService.getCurrentUser();
        boolean request = friendshipService.removeFriendShip(currentUser.getUserId(), friendId);
        if (request)
            return gsonService.success("Request was successfully declined");
        else
            return gsonService.error("Something is wrong");
    }
}
