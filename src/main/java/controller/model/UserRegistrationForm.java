package controller.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

/**
 * Created by Aydar on 28.11.2014.
 */
public class UserRegistrationForm {

    @Email(message = "Incorrect email")
    private String email;

    @Size(min = 6, max = 30, message = "Password from 6 to 30 symbols")
    private String password;

    private String confirmPassword;

    @Size(min = 1)
    private String firstName;

    @Size(min = 1)
    private String lastName;

    public UserRegistrationForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
