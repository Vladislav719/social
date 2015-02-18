package repository;

import model.PhotoLikes;
import model.PostLikes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ElessarST on 18.02.2015.
 */
public interface PostLikesRepository extends CrudRepository<PostLikes, Long> {
    @Query("select pl from PostLikes pl where pl.post.postId = ?2 and pl.owner.id = ?1 ")
    public PostLikes isLiked(Long userId, Long postId);
}
