package repository;

import model.Album;
import model.PhotoLikes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ElessarST on 18.02.2015.
 */
public interface PhotoLikesRepository extends CrudRepository<PhotoLikes, Long> {
    @Query("select pl from PhotoLikes pl where pl.photo.photoId = ?2 and pl.owner.id = ?1 ")
    public PhotoLikes isLiked(Long userId, Long photoId);
}
