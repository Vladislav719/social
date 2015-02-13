package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Created by ElessarST on 13.02.2015.
 */
public interface UserLoginService {

    public User getCurrentUser();
}
