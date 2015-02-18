package model;

import com.google.gson.annotations.Expose;
import controller.api.model.UserInfoForm;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Expose
    private User user;

    @Column(name = "first_name")
    @Expose
    private String firstName;

    @Column(name = "last_name")
    @Expose
    private String lastName;

    @Expose
    private String city;
    @Expose
    private Date lastActivity;

    @Column(name = "phone_number")
    @Expose
    private String phoneNumber;

    @Column(name = "birth_date")
    @Expose
    private Date birthDate;

    @Column(name = "about_me")
    @Expose
    private String aboutMe;

    @OneToOne
    @JoinColumn(name= "photo_id")
    @Expose
    private Photo photo;

    @OneToMany(mappedBy = "owner")
    @Expose
    private List<Album> albums;

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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public UserInfo update(UserInfoForm userInfoForm) {
        this.firstName = userInfoForm.getFirstName();
        this.lastName = userInfoForm.getLastName();
        this.city = userInfoForm.getCity();
        this.phoneNumber = userInfoForm.getPhoneNumber();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.util.Date date = null;
        try {
            date = format.parse(userInfoForm.getBirthDate());
        } catch (Exception e) {}
        this.setBirthDate(new Date(date.getTime()));
        this.aboutMe = userInfoForm.getAboutMe();
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }


}
