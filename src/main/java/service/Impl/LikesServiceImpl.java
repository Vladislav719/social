package service.Impl;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PhotoLikesRepository;
import repository.PhotoRepository;
import repository.PostLikesRepository;
import repository.PostRepository;
import service.LikesService;
import service.UserLoginService;

import java.util.ArrayList;

/**
 * Created by ElessarST on 18.02.2015.
 */
@Service
public class LikesServiceImpl implements LikesService{

    @Autowired
    private PhotoLikesRepository photoLikesRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserLoginService userLoginService;

    @Override
    public boolean addLikePhoto(long photo) {
        Photo likedPhoto = photoRepository.findOne(photo);
        UserInfo userInfo = userLoginService.getCurrentUserInfo();
        PhotoLikes likes = photoLikesRepository.isLiked(userInfo.getId(),likedPhoto.getPhotoId());
        if (likes == null){
            PhotoLikes photoLikes = new PhotoLikes();
            photoLikes.setOwner(userInfo);
            photoLikes.setPhoto(likedPhoto);
            photoLikesRepository.save(photoLikes);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeLikePhoto(long photo) {
        Photo likedPhoto = photoRepository.findOne(photo);
        UserInfo userInfo = userLoginService.getCurrentUserInfo();
        PhotoLikes likes = photoLikesRepository.isLiked(userInfo.getId(), likedPhoto.getPhotoId());
        if (likes != null){
            photoLikesRepository.delete(likes);
            likedPhoto.getLikes().remove(likes);
            return true;
        }
        return false;
    }

    @Override
    public boolean addLikePost(long post) {
        Post likedPost = postRepository.findOne(post);
        UserInfo userInfo = userLoginService.getCurrentUserInfo();
        PostLikes likes = postLikesRepository.isLiked(userInfo.getId(), likedPost.getPostId());
        if (likes == null){
            PostLikes postLikes = new PostLikes();
            postLikes.setOwner(userInfo);
            postLikes.setPost(likedPost);
            postLikesRepository.save(postLikes);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeLikePost(long post) {
        Post likedPost = postRepository.findOne(post);
        UserInfo userInfo = userLoginService.getCurrentUserInfo();
        PostLikes likes = postLikesRepository.isLiked(userInfo.getId(), likedPost.getPostId());
        if (likes != null){
            postLikesRepository.delete(likes);
            return true;
        }
        return false;
    }
}
