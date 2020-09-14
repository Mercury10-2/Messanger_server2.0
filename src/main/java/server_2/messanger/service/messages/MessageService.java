package server_2.messanger.service.messages;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.domain.users.User;
import server_2.messanger.dto.MessagePageDto;

public interface MessageService {
 /*   
    List<Message> getMessages();

    Message getMessage(Long id);

    Message create(Message message);

    Message update(Long id, Message message);

    void delete(Long id);       //  User
*/
	MessagePageDto findForUser(Pageable pageable, User user);

	Message create(Message message, User user) throws IOException;

	Message update(Message messageFromDb, Message message) throws IOException;

	void delete(Message message);
}