package service;

import controller.model.UserRegistrationForm;
import model.User;

/**
 * Created by Aydar on 27.11.2014.
 */
public interface UserService {


    public User getSecureUserByEmail(String email);

    public User findByEmail(String email);

    public User createUser(UserRegistrationForm userRegistratinForm);
}
