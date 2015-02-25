package service.Impl;

import com.google.gson.Gson;
import controller.api.model.UserInfoForm;
import controller.model.UserRegistrationForm;
import model.Photo;
import model.User;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserInfoRepository;
import repository.UserRepository;
import service.FriendshipService;
import service.PhotoService;
import service.UserLoginService;
import service.UserService;
import service.gson.GsonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    UserLoginService userLoginService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private FriendshipService friendshipService;

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

    @Override
    public ArrayList<HashMap<String, Object>> getAllPeople(long id) {
        List<UserInfo> users = userInfoRepository.getAllPeople(id);
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        for (UserInfo userInfo : users){
            Long status = friendshipService.isFriend(id, userInfo.getId());
            if (status != null && status == 1)
                continue;
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", userInfo);
            map.put("status", status);
            if (userInfo.getMainPhoto() == null)
                map.put("photo", new Photo(GsonService.defaultPhoto));
            else
                map.put("photo", userInfo.getMainPhoto());
            result.add(map);
        }
        return result;
    }

}
