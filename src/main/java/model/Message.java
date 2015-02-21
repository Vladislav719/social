package model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Aydar on 25.11.2014.
 */
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Expose
    private long messageId;

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    @Expose
    @Fetch(FetchMode.JOIN)
    private UserInfo from;

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @Expose
    private UserInfo to;

    @Expose
    private String text;

    @Expose
    private Date date;

    @Expose
    private boolean read;

    public Message() {
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


}
