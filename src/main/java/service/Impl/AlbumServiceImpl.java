package service.Impl;

import model.Album;
import model.Photo;
import model.User;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AlbumRepository;
import repository.PhotoRepository;
import repository.UserInfoRepository;
import service.AlbumService;
import service.UserService;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by ElessarST on 18.02.2015.
 */
@Service
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Album createStandardAlbum(User user) {
        UserInfo userInfo = userService.getUserInfo(user.getUserId());
        Album album = new Album();
        album.setOwner(userInfo);
        album.setType(1L);
        album.setAlbumName("Main photos");
        albumRepository.save(album);
        return album;
    }

    @Override
    public Album defaultUserAlbum(UserInfo userInfo) {
        return albumRepository.getDefaultUserAlbum(userInfo.getId());
    }


    @Override
    public Photo setUserPhoto(String relativeUrl, UserInfo currentUserInfo) {
        Album album = defaultUserAlbum(currentUserInfo);
        return addPhotoToAlbum(relativeUrl, album);
    }

    @Override
    public Photo addPhotoToAlbum(String relativeUrl, Album album) {
        Photo photo = new Photo();
        photo.setPhotoPath(relativeUrl);
        photo.setUploadDate(new Date(new java.util.Date().getTime()));
        photo.setAlbum(album);
        photoRepository.save(photo);
        return photo;
    }
}
