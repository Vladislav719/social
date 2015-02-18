package repository;

import model.Message;
import model.Photo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ElessarST on 18.02.2015.
 */
public interface PhotoRepository extends CrudRepository<Photo, Long> {

}
