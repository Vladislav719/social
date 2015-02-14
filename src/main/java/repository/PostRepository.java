package repository;

import model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ElessarST on 15.02.2015.
 */
public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("Select post from Post post where post.profile.user.id = ?1 order by post.createDate")
    List<Post> findProfilePosts(Long profileId);
}
