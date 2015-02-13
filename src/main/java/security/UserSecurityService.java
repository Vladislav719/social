package security;

import model.User;

/**
 * Created by Aydar on 27.11.2014.
 */
public interface UserSecurityService {

    public User getUser(String email);
}
