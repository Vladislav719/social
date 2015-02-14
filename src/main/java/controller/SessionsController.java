package controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserLoginService;
import service.gson.GsonService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ElessarST on 13.02.2015.
 */
@Controller
@RequestMapping("/session")
public class SessionsController {

    @Autowired
    GsonService gsonService;

    @Autowired
    UserLoginService userLoginService;

    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody
    Object registerUser(HttpServletResponse response) throws IOException {
        Gson gson = gsonService.standardBuilder();
        if (!userLoginService.isAuthenticated()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return userLoginService.notAuthError();
        } else {
            return gson.toJson(userLoginService.getCurrentUser());
        }
    }
}
