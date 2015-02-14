package service.gson;

import com.google.gson.Gson;

/**
 * Created by ElessarST on 14.02.2015.
 */
public interface GsonService {
    public Gson standardBuilder();

    public String error(String errorMessage);

    public String success(String successMessage);
}
