package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Aydar on 29.11.2014.
 */
@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @Column(name= "notification_id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "receiving_id")
    private long receivingId;

    @Column(name = "date")
    private Date date;

    private long type;

    @Column(name = "is_send")
    private boolean isSend;

    public Notifications() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(long receivingId) {
        this.receivingId = receivingId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean isSend) {
        this.isSend = isSend;
    }
}
