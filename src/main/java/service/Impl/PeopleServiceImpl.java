package service.Impl;

import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.PeopleService;
import service.UserLoginService;
import service.UserService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ElessarST on 24.02.2015.
 */
@Service
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserService userService;

    @Override
    public ArrayList<HashMap<String, Object>> getAllPeople() {
        UserInfo user = userLoginService.getCurrentUserInfo();
        return userService.getAllPeople(user.getId());
    }
}
