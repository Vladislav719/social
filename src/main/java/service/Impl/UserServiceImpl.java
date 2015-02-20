package service.Impl;

import controller.api.model.UserInfoForm;
import controller.model.UserRegistrationForm;
import model.Photo;
import model.User;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserInfoRepository;
import repository.UserRepository;
import service.PhotoService;
import service.UserService;

/**
 * Created by Aydar on 27.11.2014.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PhotoService photoService;

    @Override
    public User getSecureUserByEmail(String email) {
        return userRepository.getSecureUserByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.getSecureUserByEmail(email);
    }

    @Override
    public User createUser(UserRegistrationForm userRegistrationForm) {
        User newUser = new User(userRegistrationForm.getEmail(), userRegistrationForm.getPassword());
        UserInfo userInfo = new UserInfo(userRegistrationForm.getFirstName(), userRegistrationForm.getLastName());
        newUser.setUserInfo(userInfo);
        userInfo.setUser(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public UserInfo getUserInfo(Long profileId) {
        return userInfoRepository.getUserInfo(profileId);
    }

    @Override
    public UserInfo updateUserInfo(UserInfoForm userInfoForm, long profileId) {
        UserInfo userInfo = userInfoRepository.findOne(profileId);
        userInfo.update(userInfoForm);
        userInfoRepository.save(userInfo);
        return userInfo;
    }

    @Override
    public Photo setUserPhoto(String relativeUrl, UserInfo currentUserInfo) {
        Photo photo = photoService.addPhoto(relativeUrl, currentUserInfo);
        currentUserInfo.setMainPhoto(photo);
        userInfoRepository.save(currentUserInfo);

        return photo;
    }

    @Override
    public Photo getMainPhoto(long id) {
        return userInfoRepository.getMainPhoto(id);
    }

}
