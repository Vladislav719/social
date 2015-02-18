package model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

/**
 * Created by ElessarST on 18.02.2015.
 */
@Entity
@Table(name = "photo_likes",
        uniqueConstraints=
        @UniqueConstraint(columnNames = {"photo_id", "user_id"}))
public class PhotoLikes {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "like_id")
    @Expose
    private long likeId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Expose
    private UserInfo owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="photo_id", nullable = false)
    private Photo photo;

    public long getLikeId() {
        return likeId;
    }

    public void setLikeId(long likeId) {
        this.likeId = likeId;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }


    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}
