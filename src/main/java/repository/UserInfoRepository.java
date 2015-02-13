package repository;

import model.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Aydar on 29.11.2014.
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long>{
}
