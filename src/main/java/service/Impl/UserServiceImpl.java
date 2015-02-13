package service.Impl;

import controller.model.UserRegistrationForm;
import model.User;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserInfoRepository;
import repository.UserRepository;
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

    @Override
    public User getSecureUserByEmail(String email) {
        return userRepository.getSecureUserByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.getSecureUserByEmail(email);
    }

    @Override
    public User createUser(UserRegistrationForm userRegistratinForm) {
        User newUser = new User(userRegistratinForm.getEmail(), userRegistratinForm.getPassword());
        UserInfo userInfo = new UserInfo(userRegistratinForm.getFirstName(), userRegistratinForm.getLastName());
        newUser.setUserInfo(userInfo);
        userInfo.setUser(newUser);
        return userRepository.save(newUser);
    }
}