package controller.api;

import controller.model.MessageText;
import model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.BindingResultErrorUtil;
import service.MessagesService;
import service.UserLoginService;
import service.gson.GsonService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ElessarST on 21.02.2015.
 */
@RestController
public class MessagesApi {

    @Autowired
    private GsonService gson;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private BindingResultErrorUtil errorUtil;

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public @ResponseBody Object getDialogs(HttpServletResponse response){
        if (!userLoginService.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not authorized");
        }
        return gson.builderWithDateAndTime().toJson(messagesService.getDialogs(userLoginService.getCurrentUserInfo()));
    }

    @RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
    public @ResponseBody Object getMessages(@PathVariable long id, HttpServletResponse response){
        if (!userLoginService.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not authorized");
        }
        return gson.builderWithDateAndTime().toJson(messagesService.getMessages(userLoginService.getCurrentUserInfo(), id));
    }

    @RequestMapping(value = "/messages/{id}", method = RequestMethod.POST)
    public @ResponseBody Object sendMessages(@PathVariable long id,
                                             @RequestBody MessageText message,
                                             HttpServletResponse response){
        if (!userLoginService.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not authorized");
        }
        return gson.builderWithDateAndTime().toJson(messagesService.addMessages(userLoginService.getCurrentUserInfo(), id, message.getText()));
    }

}
