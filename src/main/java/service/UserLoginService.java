package service;

import com.google.gson.Gson;
import model.User;
import org.springframework.validation.BindingResult;

/**
 * Created by ElessarST on 13.02.2015.
 */
public interface UserLoginService {

    public boolean isAuthenticated();

    public User getCurrentUser();

    public boolean isCurrentUser(long profileId);

    public boolean isCurrentUser(long profileId, BindingResult bindingResult);

    public String notAuthError();
}
