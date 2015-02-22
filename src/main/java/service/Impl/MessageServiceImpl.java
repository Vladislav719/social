package service.Impl;

import controller.api.model.Dialog;
import model.Message;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.MessageRepository;
import service.FriendshipService;
import service.MessagesService;
import service.UserLoginService;
import service.UserService;

import java.util.*;

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
        List<UserInfo> friends = messageRepository.getFromDialogsUser(currentUserInfo.getId());
        friends.addAll(messageRepository.getToDialogsUser(currentUserInfo.getId()));
        List<Dialog> result =  new ArrayList<>();
        HashSet<Long> added = new HashSet<>();
        for (UserInfo friend : friends){
            if (!added.contains(friend.getId())) {
                result.add(new Dialog(friend, messageRepository.getLastMessage(currentUserInfo.getId(), friend.getId(), new PageRequest(0, 1)),friend.getMainPhoto()));
                added.add(friend.getId());
            }
        }
        Collections.sort(result, new Comparator<Dialog>() {
            @Override
            public int compare(Dialog o1, Dialog o2) {
                return o1.getMessage().get(0).getDate().compareTo(o2.getMessage().get(0).getDate());
            }
        });
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
        message.setDate(new java.util.Date());
        message.setRead(false);
        return messageRepository.save(message);
    }

    @Override
    public void readMessages(UserInfo currentUserInfo, long id) {
        List<Message> messages = messageRepository.setReadMessage(currentUserInfo.getId(), id);
        for (Message message : messages){
            message.setRead(true);
        }
        messageRepository.save(messages);
    }

    @Override
    public List<Message> getLastMessages(UserInfo currentUserInfo, Long messageId) {
        if (messageId == null)
            messageId = 1L;
        return messageRepository.getLastMessages(currentUserInfo.getId(), messageId);
    }
}
