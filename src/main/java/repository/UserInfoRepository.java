package repository;

import model.Photo;
import model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Aydar on 29.11.2014.
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long>{

    @Query("select userInfo from UserInfo userInfo where userInfo.user.userId = ?1 ")
    public UserInfo getUserInfo(Long profileId);

    @Query("update UserInfo userInfo set photo = ?2 where userInfo.id = ?1")
    public void setMainPhoto(long userId, Photo photo);

    @Query("select user.mainPhoto from UserInfo user where user.id = ?1 ")
    public Photo getMainPhoto(long id);
    
}
