package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.model.UserRegistrationForm;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ElessarST on 13.02.2015.
 */
@Controller
@RequestMapping("/session")
public class SessionsController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    UserLoginService userLoginService;

    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody
    Object registerUser() throws IOException {
        User user = userLoginService.getCurrentUser();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        if (user == null){
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "not login");
            return gson.toJson(error);
        } else {
            return gson.toJson(user);
        }
    }
}
