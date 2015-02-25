package service.Impl;

import HelperClasses.FriendshipStatus;
import model.Friendship;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.FriendshipsRepository;
import service.FriendshipService;
import service.UserService;

import java.util.List;

/**
 * Created by ElessarST on 14.02.2015.
 */
@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipsRepository friendshipsRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Friendship> getAllIncomingRequests(long userId) {
        return friendshipsRepository.getAllIncomingRequest(userId);
    }

    @Override
    public List<Friendship> getAllOutcomingRequests(long userId) {
        return friendshipsRepository.getAllOutcomingRequest(userId);
    }

    @Override
    public List<UserInfo> getAllFriends(long userId) {
        return friendshipsRepository.getAllFriends(userId);
    }

    @Override
    public List<Friendship> getAllRequests(long userId) {
        return friendshipsRepository.getAllRequest(userId);
    }

    @Override
    @Transactional
    public void addRequest(long userId, long friendId) {
        Friendship incoming = friendshipsRepository.getIncomingRequest(userId, friendId);
        if (incoming != null){
            incoming.setStatus(FriendshipStatus.ACCEPTED.getStatus());
            friendshipsRepository.save(incoming);
            Friendship friendship = new Friendship();
            friendship.setFrom(userService.getUserInfo(userId));
            friendship.setTo(userService.getUserInfo(friendId));
            friendship.setStatus(FriendshipStatus.ACCEPTED.getStatus());
            friendshipsRepository.save(friendship);
            return;
        }
        Friendship friendship = new Friendship();
        friendship.setFrom(userService.getUserInfo(userId));
        friendship.setTo(userService.getUserInfo(friendId));
        friendship.setStatus(FriendshipStatus.SENDED.getStatus());
        friendshipsRepository.save(friendship);
    }

    @Override
    @Transactional
    public boolean declineRequest(long userId, long friendId) {
        Friendship friendShip = friendshipsRepository.findRequest(friendId, userId);
        if (friendShip == null)
            return false;
        friendShip.setStatus(FriendshipStatus.DECLINED.getStatus());
        friendshipsRepository.save(friendShip);
        return true;
    }

    @Override
    @Transactional
    public boolean acceptRequest(long userId, long friendId) {
        Friendship friendship = friendshipsRepository.findRequest(friendId, userId);
        if (friendship == null)
            return false;
        friendship.setStatus(FriendshipStatus.ACCEPTED.getStatus());
        friendshipsRepository.save(friendship);
        Friendship newFriendship = new Friendship();
        newFriendship.setFrom(userService.getUserInfo(userId));
        newFriendship.setTo(userService.getUserInfo(friendId));
        newFriendship.setStatus(FriendshipStatus.ACCEPTED.getStatus());
        friendshipsRepository.save(newFriendship);
        return true;
    }

    @Override
    public boolean removeFriendShip(long userId, long friendId) {
        Friendship friendship = friendshipsRepository.findRequest(userId, friendId);
        if (friendship == null)
            return false;
        friendshipsRepository.delete(friendship);
        Friendship reverseFriendShip = friendshipsRepository.findRequest(friendId, userId);
        if (reverseFriendShip != null){
            reverseFriendShip.setStatus(FriendshipStatus.DECLINED.getStatus());
            friendshipsRepository.save(reverseFriendShip);
        }
        return true;
    }

    @Override
    public Long isFriend(long id, long userId) {
        return friendshipsRepository.isFriends(id, userId);
    }
}
