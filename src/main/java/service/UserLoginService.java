package service;

import model.User;

/**
 * Created by ElessarST on 13.02.2015.
 */
public interface UserLoginService {

    public boolean isAuthenticated();

    public User getCurrentUser();
}
