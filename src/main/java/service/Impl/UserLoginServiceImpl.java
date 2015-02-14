package service.Impl;

import com.google.gson.Gson;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import service.UserLoginService;
import service.UserService;
import service.gson.GsonService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by ElessarST on 13.02.2015.
 */

@Service
public class UserLoginServiceImpl  implements UserLoginService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private GsonService gsonService;

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

    @Override
    public boolean isCurrentUser(long profileId) {
        return getCurrentUser().getUserId() == profileId;
    }

    @Override
    public boolean isCurrentUser(long profileId, BindingResult bindingResult) {
        boolean isCurrent = isCurrentUser(profileId);
        if (!isCurrent)
            bindingResult.rejectValue("firstName", "user", "incorrectUser");
        return isCurrent;
    }

    @Override
    public String notAuthError() {
        HashMap<String, String> error = new HashMap<>();
        error.put("error", "not login");
        return gsonService.standardBuilder().toJson(error);
    }


}
