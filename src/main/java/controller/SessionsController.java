package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserLoginService;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ElessarST on 13.02.2015.
 */
@Controller
@RequestMapping("/session")
public class SessionsController {


    @Autowired
    UserLoginService userLoginService;

    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody
    Object registerUser() throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        if (!userLoginService.isAuthenticated()){
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "not login");
            return gson.toJson(error);
        } else {
            return gson.toJson(userLoginService.getCurrentUser());
        }
    }
}
