package service.Impl;

import controller.api.model.Dialog;
import model.Message;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.MessageRepository;
import repository.UserInfoRepository;
import service.FriendshipService;
import service.MessagesService;
import service.UserLoginService;
import service.UserService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aydar on 27.11.2014.
 */
@Service
public class MessageServiceImpl implements MessagesService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserService userService;
    @Override
    public List<Dialog> getDialogs(UserInfo currentUserInfo) {
        List<UserInfo> friends = messageRepository.getAllDialogsUser(currentUserInfo.getId());
        List<Dialog> result =  new ArrayList<>();
        for (UserInfo user : friends){
            result.add(new Dialog(user, messageRepository.getLastMessage(currentUserInfo.getId(), user.getId()), new PageRequest(0, 1)));
        }
        return result;
    }

    @Override
    public List<Message> getMessages(UserInfo currentUserInfo, long id) {
        return messageRepository.getMessages(currentUserInfo.getId(), id);
    }

    @Override
    public Message addMessages(UserInfo currentUserInfo, long id, String text) {
        Message message = new Message();
        message.setFrom(currentUserInfo);
        message.setTo(userService.getUserInfo(id));
        message.setText(text);
        message.setDate(new Date(new java.util.Date().getTime()));
        message.setRead(false);
        return messageRepository.save(message);
    }
}
