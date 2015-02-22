package controller.api;

import controller.model.MessageText;
import model.Message;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import service.BindingResultErrorUtil;
import service.MessagesService;
import service.UserLoginService;
import service.gson.GsonService;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ElessarST on 21.02.2015.
 */
@RestController
public class MessagesApi {

    private final Map<Long, DeferredResult<String>> chatRequests =
            new ConcurrentHashMap<>();

    @Autowired
    private GsonService gson;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private BindingResultErrorUtil errorUtil;

    @RequestMapping(value = "updates/messages", method = RequestMethod.GET)
    public @ResponseBody DeferredResult<String> updateMessages(
            @RequestParam(required = false) Long messageId){
        final DeferredResult<String> deferredResult= new DeferredResult<>();
        this.chatRequests.put(userLoginService.getCurrentUserInfo().getId(), deferredResult);
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                chatRequests.remove(deferredResult);
            }
        });
        List<Message> messages = messagesService.getLastMessages(userLoginService.getCurrentUserInfo(), messageId);
        if (!messages.isEmpty())
            deferredResult.setResult(gson.builderWithDateAndTime().toJson(messages));
        return deferredResult;
    }

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
        if (message.getText().equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gson.error("Text can't be empty");
        }
        UserInfo user = userLoginService.getCurrentUserInfo();
        Message addedMessage = messagesService.addMessages(user, id, message.getText());
        Long secondUserId = addedMessage.getFrom().getId() == user.getId() ? addedMessage.getTo().getId() :
                addedMessage.getFrom().getId();
        if (chatRequests.containsKey(secondUserId)){
            DeferredResult<String> deferredResult = chatRequests.get(secondUserId);
            deferredResult.setResult(gson.builderWithDateAndTime().toJson(message));
        }
        return gson.builderWithDateAndTime().toJson(addedMessage);
    }

    @RequestMapping(value = "/messages/read/{id}", method = RequestMethod.POST)
    public @ResponseBody Object readMessages(@PathVariable long id,
                                             HttpServletResponse response){
        if (!userLoginService.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not authorized");
        }
        messagesService.readMessages(userLoginService.getCurrentUserInfo(), id);
        return gson.success("success");
    }

}
