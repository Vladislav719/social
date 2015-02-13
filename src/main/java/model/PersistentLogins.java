package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by Aydar on 27.11.2014.
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Column(name = "username", nullable = false)
    public String username;

    @Id
    @Column(name = "series", nullable = false)
    public String series;

    @Column(name = "token", nullable = false)
    public String token;

    @Column(name = "last_used", nullable = false)
    public Date lastused;

    public PersistentLogins() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastused() {
        return lastused;
    }

    public void setLastused(Date lastused) {
        this.lastused = lastused;
    }
}
