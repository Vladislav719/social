package service;

import controller.api.model.PostEdit;
import controller.api.model.PostForm;
import model.Post;

import java.util.List;

/**
 * Created by ElessarST on 15.02.2015.
 */
public interface PostService {

    public Post addPost(PostForm postForm);

    public boolean deletePost(long postId);

    public Post editPost(long postId, PostEdit postForm);

    public Post findPost(long postId);

    public List<Post> findProfilePosts(Long profileId);
}
