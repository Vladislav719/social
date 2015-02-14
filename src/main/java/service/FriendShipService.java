package service;

import model.Friendship;
import model.UserInfo;

import java.util.List;

/**
 * Created by ElessarST on 14.02.2015.
 */
public interface FriendshipService {

    public List<UserInfo> getAllFriends(long userId);

    public List<Friendship> getAllOutcomingRequests(long userId);

    public List<Friendship> getAllIncomingRequests(long userId);

    public List<Friendship> getAllRequests(long userId);

    public boolean acceptRequest(long userId, long friendId);

    public boolean declineRequest(long userId, long friendId);

    public void addRequest(long userId, long friendId);

    public boolean removeFriendShip(long userId, long friendId);
}
