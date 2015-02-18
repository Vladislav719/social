package controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.LikesService;
import service.UserLoginService;
import service.gson.GsonService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ElessarST on 19.02.2015.
 */
@RestController
public class LikesApi {

    @Autowired
    LikesService likesService;

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    GsonService gson;

    @RequestMapping(value = "/likes/post/{postId}", method = RequestMethod.POST)
    public @ResponseBody Object addPostLike(@PathVariable long postId, HttpServletResponse response){
        if (userLoginService.getCurrentUserInfo() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not logged");
        }
        if (likesService.addLikePost(postId)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return gson.success("Success");
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gson.error("Something is wrong");
        }
    }

    @RequestMapping(value = "/likes/post/{postId}", method = RequestMethod.DELETE)
    public @ResponseBody Object removePostLike(@PathVariable long postId, HttpServletResponse response){
        if (userLoginService.getCurrentUserInfo() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not logged");
        }
        if (likesService.removeLikePost(postId)){
            response.setStatus(HttpServletResponse.SC_OK);
            return gson.success("Success");
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gson.error("Something is wrong");
        }
    }

    @RequestMapping(value = "/likes/photo/{postId}", method = RequestMethod.POST)
    public @ResponseBody Object addPhotoLike(@PathVariable long postId, HttpServletResponse response){
        if (userLoginService.getCurrentUserInfo() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not logged");
        }
        if (likesService.addLikePhoto(postId)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return gson.success("Success");
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gson.error("Something is wrong");
        }
    }

    @RequestMapping(value = "/likes/photo/{postId}", method = RequestMethod.DELETE)
    public @ResponseBody Object removePhotoLike(@PathVariable long postId, HttpServletResponse response) {
        if (userLoginService.getCurrentUserInfo() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return gson.error("You're not logged");
        }
        if (likesService.removeLikePhoto(postId)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return gson.success("Success");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gson.error("Something is wrong");
        }
    }
}
