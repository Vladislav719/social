package controller.api.model;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PeopleService;
import service.UserService;
import service.gson.GsonService;

/**
 * Created by ElessarST on 24.02.2015.
 */
@Controller
public class PeopleController {

    @Autowired
    private PeopleService peopleService;


    @Autowired
    private GsonService gson;

    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public @ResponseBody Object allPeople(){
        return gson.builderWithDate().toJson(peopleService.getAllPeople());
    }
}
