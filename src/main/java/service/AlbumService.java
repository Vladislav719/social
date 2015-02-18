package service;

import model.Album;
import model.Photo;
import model.User;
import model.UserInfo;

/**
 * Created by ElessarST on 18.02.2015.
 */
public interface AlbumService {

    public Album createStandardAlbum(User userInfo);

    public Album defaultUserAlbum(UserInfo userInfo);

    public Photo setUserPhoto(String relativeUrl, UserInfo currentUserInfo);

    public Photo addPhotoToAlbum(String photo, Album album);
}
