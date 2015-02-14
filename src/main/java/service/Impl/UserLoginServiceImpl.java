package service.Impl;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import service.UserLoginService;
import service.UserService;

/**
 * Created by ElessarST on 13.02.2015.
 */

@Service
public class UserLoginServiceImpl  implements UserLoginService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public boolean isAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    @Override
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  userService.findByEmail(user.getUsername());
    }
}
