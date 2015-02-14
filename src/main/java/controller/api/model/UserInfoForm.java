package controller.api.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by ElessarST on 14.02.2015.
 */
public class UserInfoForm {
    @NotNull
    @Size(min = 1, message = "First name can't be empty")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "First name can't be empty")
    private String lastName;

    private String city;

    public UserInfoForm(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = "";
    }

    public UserInfoForm() {
    }

    public UserInfoForm(String firstName, String lastName, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
