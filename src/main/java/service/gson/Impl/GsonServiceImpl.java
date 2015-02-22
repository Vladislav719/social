package service.gson.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.FriendshipService;
import service.UserLoginService;
import service.UserService;
import service.gson.GsonService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ElessarST on 14.02.2015.
 */
@Service
public class GsonServiceImpl implements GsonService {
    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserLoginService userLoginService;

    @Override
    public Gson standardBuilder() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Override
    public Gson builderWithDate() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd").create();
    }

    @Override
    public Gson builderWithDateAndTime() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("MMM d, y h:mm:ss a").create();
    }

    @Override
    public Map<String, Object> includePhotos(UserInfo userInfo, boolean includePhotos) {
        Map<String, Object> user = new HashMap<>();
        user.put("userInfo", userInfo);
        if (includePhotos)
            user.put("photos", userInfo.getPhoto());
        user.put("status", friendshipService.isFriend(userLoginService.getCurrentUserInfo().getId(), userInfo.getId()));
        user.put("photo", userInfo.getMainPhoto());
        return user;
    }


    public String error(String errorMessage){
        HashMap<String, String> error = new HashMap<>();
        error.put("error", errorMessage);
        return standardBuilder().toJson(error);
    }

    public String success(String successMessage){
        HashMap<String, String> success = new HashMap<>();
        success.put("success", successMessage);
        return standardBuilder().toJson(success);
    }
}
