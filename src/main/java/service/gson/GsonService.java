package service.gson;

import com.google.gson.Gson;
import model.UserInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ElessarST on 14.02.2015.
 */
public interface GsonService {
    public static String defaultPhoto = "/resources/images/default/default.jpg";

    public Gson standardBuilder();

    public String error(String errorMessage);

    public HashMap<String, Object> loginError(HttpServletResponse response);

    public String success(String successMessage);

    public Gson builderWithDate();

    public Gson builderWithDateAndTime();

    public Map<String, Object> includePhotos(UserInfo userInfo, boolean includePhotos);
}
