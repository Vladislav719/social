package service.Impl;

import controller.api.model.PostEdit;
import controller.api.model.PostForm;
import model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PostRepository;
import service.PostService;
import service.UserService;

import java.util.List;

/**
 * Created by ElessarST on 15.02.2015.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Override
    public Post editPost(long postId, PostEdit postEdit) {
        Post post = postRepository.findOne(postId);
        if (post == null)
            return null;
        post.update(postEdit.getText());
        post = (Post)postRepository.save(post);
        return post;
    }

    @Override
    public boolean deletePost(long postId) {
        Post post = postRepository.findOne(postId);
        if (post == null)
            return false;
        else
            postRepository.delete(post);
        return true;
    }

    @Override
    public Post addPost(PostForm postForm) {
        Post post = new Post(userService.getUserInfo(postForm.getAuthorId()),
                userService.getUserInfo(postForm.getProfileId()), postForm.getText());
        post = (Post)postRepository.save(post);
        return post;
    }

    @Override
    public Post findPost(long postId) {
        return postRepository.findOne(postId);
    }

    @Override
    public List<Post> findProfilePosts(Long profileId) {
        return postRepository.findProfilePosts(profileId);
    }
}
