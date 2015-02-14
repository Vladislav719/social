package repository;

import model.Friendship;
import model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ElessarST on 14.02.2015.
 */
public interface FriendshipsRepository extends CrudRepository<Friendship, Long> {

    @Query("select friends from Friendship friends where friends.to.user.userId = ?1 and friends.status = 0")
    public List<Friendship> getAllIncomingRequest(long userId);

    @Query("select friends from Friendship friends where friends.from.user.userId = ?1 and (friends.status = 0 or friends.status = -1)")
    public List<Friendship> getAllOutcomingRequest(long userId);

    @Query("select friends.to from Friendship friends where friends.from.user.userId = ?1 and friends.status = 1")
    public List<UserInfo> getAllFriends(long userId);

    @Query("select friends from Friendship friends where friends.to.user.userId = ?1 or friends.from.user.userId = ?1")
    public List<Friendship> getAllRequest(long userId);

    @Query("select friends from Friendship friends where friends.from.user.userId = ?1 and friends.to.user.userId = ?2")
    public Friendship findRequest(long userId, long friendId);
}
