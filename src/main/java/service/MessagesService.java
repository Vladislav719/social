package service;

import controller.api.model.Dialog;
import model.Message;
import model.UserInfo;

import java.util.List;

/**
 * Created by ElessarST on 21.02.2015.
 */
public interface MessagesService {

    public List<Dialog> getDialogs(UserInfo currentUserInfo);

    public List<Message> getMessages(UserInfo currentUserInfo, long id);

    public Message addMessages(UserInfo currentUserInfo, long id, String text);

    public void readMessages(UserInfo currentUserInfo, long id);

    public List<Message> getLastMessages(UserInfo currentUserInfo, Long messageId);
}
