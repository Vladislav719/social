package model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Aydar on 25.11.2014.
 */
@Entity
@Table(name = "friends",
        uniqueConstraints=
        @UniqueConstraint(columnNames = {"from_id", "to_id"}))
public class Friendship {
    @Id
    @GeneratedValue(generator="increment")
    @Expose
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id", nullable = false)
    @Expose
    private UserInfo from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id", nullable = false)
    @Expose
    private UserInfo to;

    private long status;

    public Friendship() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public UserInfo getFrom() {
        return from;
    }

    public void setFrom(UserInfo from) {
        this.from = from;
    }

    public UserInfo getTo() {
        return to;
    }

    public void setTo(UserInfo to) {
        this.to = to;
    }

    public long getStatus() {
        return status;
    }
}
