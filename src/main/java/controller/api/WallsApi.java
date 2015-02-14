package controller.api;

import controller.api.model.PostEdit;
import controller.api.model.PostForm;
import model.Post;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.BindingResultErrorUtil;
import service.PostService;
import service.UserLoginService;
import service.gson.GsonService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by ElessarST on 15.02.2015.
 */
@RestController
public class WallsApi {

    @Autowired
    BindingResultErrorUtil bindingResultErrorUtil;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private PostService postService;

    @Autowired
    GsonService gsonService;

    @RequestMapping(value = "/walls/add", method = RequestMethod.POST)
    public @ResponseBody Object addPost(@RequestBody @Valid PostForm postForm,
                                        BindingResult bindingResult, HttpServletResponse response){
        User currentUser = userLoginService.getCurrentUser();
        if (bindingResult.hasErrors()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gsonService.standardBuilder().toJson(bindingResultErrorUtil.returnJson(bindingResult));
        }
        postForm.setAuthorId(currentUser.getUserId());
        Post post = postService.addPost(postForm);
        return gsonService.standardBuilder().toJson(post);
    }

    @RequestMapping(value = "/walls/remove/{postId}", method = RequestMethod.DELETE)
    public @ResponseBody Object removePost(@PathVariable Long postId, HttpServletResponse response){
        User currentUser = userLoginService.getCurrentUser();
        Post post = postService.findPost(postId);
        if (post.getAuthor().getId() != currentUser.getUserId() ||
                post.getProfile().getId() != currentUser.getUserId()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gsonService.error("You don't have permission");
        }
        postService.deletePost(postId);
        return gsonService.success("Post successfully removed");
    }

    @RequestMapping(value = "/walls/edit/{postId}", method = RequestMethod.PUT)
    public @ResponseBody Object deletePost(@PathVariable Long postId,
                                           @RequestBody @Valid PostEdit postEdit,
                                           BindingResult bindingResult, HttpServletResponse response){
        User currentUser = userLoginService.getCurrentUser();
        Post post = postService.findPost(postId);
        if (post.getAuthor().getId() != currentUser.getUserId() ||
                post.getProfile().getId() != currentUser.getUserId()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gsonService.error("You don't have permission");
        }
        if (bindingResult.hasErrors()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gsonService.standardBuilder().toJson(bindingResultErrorUtil.returnJson(bindingResult));
        }
        post = postService.editPost(postId, postEdit);
        if (post == null)
            return gsonService.error("Post doesn't exist");
        return gsonService.success("Post successfully removed");
    }

    @RequestMapping(value = "/walls/get/{postId}", method = RequestMethod.GET)
    public @ResponseBody Object getPost(@PathVariable Long postId, HttpServletResponse response) {
        Post post = postService.findPost(postId);
        if (post == null)
            return gsonService.error("Post doesn't exist");
        return  gsonService.standardBuilder().toJson(post);
    }

    @RequestMapping(value = "/walls/get/all/{profileId}", method = RequestMethod.GET)
    public @ResponseBody Object getPosts(@PathVariable Long profileId, HttpServletResponse response) {
        List<Post> posts = postService.findProfilePosts(profileId);
        if (posts == null)
            return gsonService.error("Post doesn't exist");
        return  gsonService.standardBuilder().toJson(posts);
    }

}
