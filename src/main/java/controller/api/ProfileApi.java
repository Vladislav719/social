package controller.api;

import com.google.gson.Gson;
import controller.api.model.UserInfoForm;
import model.Photo;
import model.User;
import model.UserInfo;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.BindingResultErrorUtil;
import service.PhotoService;
import service.UserLoginService;
import service.UserService;
import service.gson.GsonService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

/**
 * Created by ElessarST on 14.02.2015.
 */
@RestController
@RequestMapping
public class ProfileApi {


    private static final String path = "E:\\Projects\\SocialNetwork\\src\\main\\webapp\\";

    @Autowired
    BindingResultErrorUtil bindingResultErrorUtil;

    @Autowired
    ServletContext servletContext;

    @Autowired
    GsonService gsonService;

    @Autowired
    UserService userService;

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    PhotoService photoService;


    @RequestMapping(value = "/profile/userInfo/{profileId}", method = RequestMethod.GET)
    private @ResponseBody Object getUserInfo(@PathVariable Long profileId,
                                             @RequestParam boolean includePhotos,
                                             HttpServletResponse response){
        UserInfo userInfo = userService.getUserInfo(profileId);
        Gson gson = gsonService.builderWithDate();
        if (userInfo == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gsonService.error("User not found");
        }
        try {
            return gson.toJson(gsonService.includePhotos(userInfo, includePhotos));
        } catch (Exception ex){
            return gson.toJson(gsonService.loginError(response));
        }

    }

    private Map<String, Object> userWithPhoto(UserInfo userInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        map.put("photo", userService.getMainPhoto(userInfo.getId()));
        map.put("photos", userInfo.getPhoto());
        return map;
    }

    @RequestMapping(value = "/profile/userInfo", method = RequestMethod.PUT)
    private @ResponseBody Object changeUserInfo(@RequestBody @Valid UserInfoForm userInfoForm,
                                                BindingResult bindingResult,
                                                HttpServletResponse response) {
        UserInfo user = userLoginService.getCurrentUserInfo();
        if (user == null)
            return gsonService.standardBuilder().toJson(gsonService.loginError(response));
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return gsonService.standardBuilder().toJson(bindingResultErrorUtil.returnJson(bindingResult));
        } else {
            return gsonService.builderWithDate().toJson(gsonService.includePhotos(userService.updateUserInfo(userInfoForm, user.getId()), true));
        }
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                Photo photo = userService.setUserPhoto(savePhoto(file), userLoginService.getCurrentUserInfo());
                return gsonService.standardBuilder().toJson(photo);
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    @RequestMapping(value="/add/photos", method=RequestMethod.POST)
    public @ResponseBody String savePhotos(@RequestParam("file") MultipartFile file){
        String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                Photo photo = photoService.addPhoto(savePhoto(file), userLoginService.getCurrentUserInfo());
                return gsonService.standardBuilder().toJson(photo);
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    private String savePhoto(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String webappRoot = servletContext.getRealPath("/");
        String relativeFolder = File.separator +"resources" + File.separator
                + "images" + File.separator + userLoginService.getCurrentUser().getUserId() + File.separator;
        if (!Files.exists(Paths.get(webappRoot + relativeFolder)))
            Files.createDirectories(Paths.get(webappRoot+relativeFolder));
        String filename = webappRoot + relativeFolder
                + getFileName(file);
        String copyFileName = path + relativeFolder +getFileName(file);
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(filename)));
        stream.write(bytes);
        stream.close();
        if (!Files.exists(Paths.get(path + relativeFolder)))
            Files.createDirectories(Paths.get(path+relativeFolder));
        stream = new BufferedOutputStream(new FileOutputStream(new File(copyFileName)));
        stream.write(bytes);
        stream.close();
        return filename.substring((webappRoot).length());
    }

    private String getFileName(MultipartFile file){
        String name = file.getOriginalFilename();
        String format = name.substring(name.lastIndexOf('.'));
        return name.substring(0, name.lastIndexOf('.')) + (new java.util.Date()).getTime() + format;
    }

}
