package repository;

import model.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Aydar on 27.11.2014.
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

}
