package service;

import controller.api.model.UserInfoForm;
import controller.model.UserRegistrationForm;
import model.User;
import model.UserInfo;

/**
 * Created by Aydar on 27.11.2014.
 */
public interface UserService {


    public User getSecureUserByEmail(String email);

    public User findByEmail(String email);

    public User createUser(UserRegistrationForm userRegistrationForm);

    public UserInfo getUserInfo(Long profileId);

    public UserInfo updateUserInfo(UserInfoForm userInfoForm, long profileId);

    public User addDefaultAlbum(User newUser);
}
