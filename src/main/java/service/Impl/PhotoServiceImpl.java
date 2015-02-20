package service.Impl;

import model.Photo;
import model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PhotoRepository;
import service.PhotoService;

import java.sql.Date;

/**
 * Created by ElessarST on 19.02.2015.
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Override
    public Photo addPhoto(String relativeUrl, UserInfo currentUserInfo) {
        Photo photo = new Photo();
        photo.setUploadDate(new Date(new java.util.Date().getTime()));
        photo.setOwner(currentUserInfo);
        photo.setPhotoPath(relativeUrl);
        return photoRepository.save(photo);
    }
}
