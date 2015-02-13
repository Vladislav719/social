package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.model.UserRegistrationForm;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Aydar on 27.11.2014.
 */

@Controller
@RequestMapping()
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    ShaPasswordEncoder shaPasswordEncoder;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @RequestMapping(value = "/register_user", method = RequestMethod.POST)
    public @ResponseBody
    Object registerUser(@RequestBody @Valid UserRegistrationForm userRegistrationForm,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        isExist(userRegistrationForm, bindingResult);
        isPasswordEquals(userRegistrationForm, bindingResult);
        userRegistrationForm.setPassword(decodePassword(userRegistrationForm.getPassword()));
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        if (bindingResult.hasErrors()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            HashMap<String, Object> returnJSON = new HashMap<>();
            returnJSON.put("errors", getErrors(bindingResult));
            return gson.toJson(returnJSON);
        } else{
            response.setStatus(HttpServletResponse.SC_CREATED);
            User newUser = userService.createUser(userRegistrationForm);
            authenticateUserAndSetSession(newUser, request);
            return gson.toJson(newUser);
        }
    }

    private void authenticateUserAndSetSession(model.User user, HttpServletRequest request) {
        String username = user.getEmail();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

    }

    private HashMap<String, String> getErrors(BindingResult bindingResult) {
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    private String decodePassword(String password) {
        return shaPasswordEncoder.encodePassword(password, "");
    }

    private void isExist(UserRegistrationForm userRegistrationForm, Errors errors){
        if (userService.findByEmail(userRegistrationForm.getEmail()) != null)
            errors.rejectValue("email", "this email already use");
    }

    private void isPasswordEquals(UserRegistrationForm userRegistrationForm, BindingResult bindingResult) {
        if (!userRegistrationForm.getPassword().equals(userRegistrationForm.getConfirmPassword()))
            bindingResult.rejectValue("confirmPassword", "password", "passwords doesn't match");
    }

}
