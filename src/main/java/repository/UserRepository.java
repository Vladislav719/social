package repository;

import model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Aydar on 27.11.2014.
 */

public interface UserRepository extends CrudRepository<User, Long> {


    @Query("select DISTINCT user from User user where user.email=?1")
    public User getSecureUserByEmail(String email);

}
