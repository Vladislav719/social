package repository;

import model.Album;
import model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ElessarST on 18.02.2015.
 */
public interface AlbumRepository extends CrudRepository<Album, Long> {

    @Query("select album from Album album where album.owner.id = ?1")
    public Album getDefaultUserAlbum(Long userId);
}
