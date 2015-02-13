package service.Impl;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import service.UserLoginService;

/**
 * Created by ElessarST on 13.02.2015.
 */

@Service
public class UserLoginServiceImpl  implements UserLoginService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User getCurrentUser() {
        return  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
