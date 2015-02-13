package security.Impl;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.UserSecurityService;
import service.UserService;

/**
 * Created by Aydar on 27.11.2014.
 */
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    private UserService userService;

    @Override
    public User getUser(String email) {
        return userService.getSecureUserByEmail(email);
    }
}
