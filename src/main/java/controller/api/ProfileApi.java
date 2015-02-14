package controller.api;

import com.google.gson.Gson;
import controller.api.model.UserInfoForm;
import model.User;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.BindingResultErrorUtil;
import service.UserLoginService;
import service.UserService;
import service.gson.GsonService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Date;

/**
 * Created by ElessarST on 14.02.2015.
 */
@RestController
@RequestMapping
public class ProfileApi {

    @Autowired
    BindingResultErrorUtil bindingResultErrorUtil;

    @Autowired
    GsonService gsonService;

    @Autowired
    UserService userService;

    @Autowired
    UserLoginService userLoginService;


    @RequestMapping(value = "/profile/userInfo/{profileId}", method = RequestMethod.GET)
    private @ResponseBody Object getUserInfo(@PathVariable Long profileId){
        UserInfo userInfo = userService.getUserInfo(profileId);
        Gson gson = gsonService.standardBuilder();
        return gson.toJson(userInfo);
    }

    @RequestMapping(value = "/profile/userInfo/{profileId}", method = RequestMethod.PUT)
    private @ResponseBody Object changeUserInfo(@PathVariable Long profileId,
                                                @RequestBody @Valid UserInfoForm userInfoForm,
                                                BindingResult bindingResult,
                                                HttpServletResponse response) {
        try{
            userLoginService.isCurrentUser(profileId, bindingResult);
        } catch (Exception ex){
            bindingResult.rejectValue("firstName", "user", "incorrectUser");
        }
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gsonService.standardBuilder().toJson(bindingResultErrorUtil.returnJson(bindingResult));
        } else {
            return userService.updateUserInfo(userInfoForm, profileId);
        }
    }
}
