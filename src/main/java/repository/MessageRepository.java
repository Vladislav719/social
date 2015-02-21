package repository;

import controller.api.model.Dialog;
import model.Message;
import model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Aydar on 27.11.2014.
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("select distinct message from Message message where message.from.id in (?1, ?2) and message.to.id in (?1, ?2) order by message.date desc")
    public List<Message> getLastMessage(long userId, long friendId);

    @Query("select distinct case when message.to.id = ?1 then message.from else message.to end from Message message where message.to.id=?1 or message.from.id=?1")
    public List<UserInfo> getAllDialogsUser(long id);

    @Query("select message from Message message where message.from.id in (?1, ?2) and message.to.id in (?1, ?2) order by message.date")
    public List<Message> getMessages(long user, long friend);
}
