package controller.api.model;

import model.Message;
import model.UserInfo;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by ElessarST on 21.02.2015.
 */
public class Dialog {

    private List<Message> message;

    private UserInfo userInfo;

    public Dialog(UserInfo userInfo, List<Message> message, PageRequest pageRequest) {
        this.message = message;
        this.userInfo = userInfo;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
