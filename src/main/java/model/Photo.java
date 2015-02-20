package model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by ElessarST on 18.02.2015.
 */
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Expose
    @Column(name = "photo_id")
    private long photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_info_id", nullable = false)
    private UserInfo owner;

    @Expose
    private Date uploadDate;

    @Expose
    private String photoPath;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "photo", cascade = CascadeType.ALL)
    @Expose
    private List<PhotoLikes> likes;

    public Photo() {
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public List<PhotoLikes> getLikes() {
        return likes;
    }

    public void setLikes(List<PhotoLikes> likes) {
        this.likes = likes;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }
}