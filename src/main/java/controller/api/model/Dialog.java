package controller.api.model;

import com.google.gson.annotations.Expose;
import model.Message;
import model.Photo;
import model.UserInfo;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by ElessarST on 21.02.2015.
 */
public class Dialog {

    @Expose
    private List<Message> message;

    @Expose
    private UserInfo userInfo;

    @Expose
    private Photo photo;

    public Dialog(UserInfo userInfo, List<Message> message, Photo photo) {
        this.photo = photo;
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
