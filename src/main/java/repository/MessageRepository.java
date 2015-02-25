package repository;

import controller.api.model.Dialog;
import model.Message;
import model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Aydar on 27.11.2014.
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("select distinct message from Message message where message.from.id in (?1, ?2) and message.to.id in (?1, ?2) order by message.date desc")
    public List<Message> getLastMessage(long userId, long friendId, Pageable page);

    @Query("select distinct message.from from Message message where message.to.id=?1")
    public List<UserInfo> getFromDialogsUser(long id);

    @Query("select distinct message.to from Message message where message.from.id=?1")
    public List<UserInfo> getToDialogsUser(long id);

    @Query("select message from Message message where message.from.id in (?1, ?2) and message.to.id in (?1, ?2) and message.to.id <> message.from.id order by message.date")
    public List<Message> getMessages(long user, long friend);

    @Query("select message from Message message where message.from.id = ?2 and message.to.id = ?1 and message.read = false ")
    public List<Message> setReadMessage(long userId, long chatId);

    @Query("select message from Message message where message.messageId > ?2 and (message.to.id =?1) and message.read = false ")
    public List<Message> getLastMessages(long id, Long messageId);
}
