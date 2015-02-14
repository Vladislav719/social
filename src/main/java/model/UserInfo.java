package model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Aydar on 25.11.2014.
 */
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "user_info_id")
    @Expose
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name")
    @Expose
    private String firstName;

    @Column(name = "last_name")
    @Expose
    private String lastName;

    @Column(name = "birth_date")
    @Expose
    private Date birthDate;

    @Expose
    private String city;
    @Expose
    private Date lastActivity;

    @Column(name = "phone_number")
    @Expose
    private String phoneNumber;

    @Column(name = "photo_path")
    @Expose
    private String photoPath;

    public UserInfo() {
    }

    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
