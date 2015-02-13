package security.Impl;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.UserRole;
import security.UserSecurityService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aydar on 27.11.2014.
 */
@Service
public class UserDetailsSecurityServiceImpl implements UserDetailsService {
    @Autowired
    public UserSecurityService userSecurityService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userSecurityService.getUser(email);
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                roles);
    }
}
