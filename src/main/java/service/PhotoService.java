package service;

import model.Photo;
import model.UserInfo;

/**
 * Created by ElessarST on 19.02.2015.
 */
public interface PhotoService {
    public Photo addPhoto(String relativeUrl, UserInfo currentUserInfo);
}
