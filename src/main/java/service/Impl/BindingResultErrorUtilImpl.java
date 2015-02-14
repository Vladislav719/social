package service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import service.BindingResultErrorUtil;

import java.util.HashMap;

/**
 * Created by ElessarST on 14.02.2015.
 */
@Service
public class BindingResultErrorUtilImpl implements BindingResultErrorUtil {

    @Override
    public HashMap<String, Object> returnJson(BindingResult bindingResult) {
        HashMap<String, Object> returnJSON = new HashMap<>();
        returnJSON.put("errors", getErrors(bindingResult));
        return returnJSON;
    }

    private HashMap<String, String> getErrors(BindingResult bindingResult) {
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}
