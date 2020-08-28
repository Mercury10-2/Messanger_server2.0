package server_2.messanger.service.messages;

import java.util.List;

import server_2.messanger.domain.messages.Message;

public interface MessageService {
    
    List<Message> getMessages();

    Message getMessage(Long id);

    Message create(Message message);

    Message update(Long id, Message message);

    void delete(Long id);
}