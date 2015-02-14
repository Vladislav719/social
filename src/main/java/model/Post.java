package model;

import com.google.gson.annotations.Expose;
import controller.api.model.PostForm;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ElessarST on 15.02.2015.
 */
@Entity
@Table(name = "posts")
public class Post{
    @Id
    @GeneratedValue(generator="increment")
    @Expose
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    @Expose
    @NotNull
    private UserInfo author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
    @Expose
    @NotNull
    private UserInfo profile;

    @Expose
    private String text;

    @Expose
    @NotNull
    private Date createDate;

    public Post() {
    }

    public Post(UserInfo author, UserInfo profile, String text) {
        this.author = author;
        this.profile = profile;
        this.text = text;
        this.createDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }

    public UserInfo getProfile() {
        return profile;
    }

    public void setProfile(UserInfo profile) {
        this.profile = profile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void update(String text) {
        this.text = text;
        this.createDate = new Date();
    }
}
