package service;

import org.springframework.validation.BindingResult;

import java.util.HashMap;

/**
 * Created by ElessarST on 14.02.2015.
 */
public interface BindingResultErrorUtil {

    public HashMap<String, Object> returnJson(BindingResult bindingResult);
}
