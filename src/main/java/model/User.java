package model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
/**
 * Created by Aydar on 25.11.2014.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="user_id")
    @Expose
    private long userId;

    @Expose
    private String email;

    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private UserInfo userInfo;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


}
