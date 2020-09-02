package server_2.messanger.service.users;

import org.springframework.http.ResponseEntity;

import server_2.messanger.domain.messages.Message;

public interface ModerService {
    
    ResponseEntity<Message> updateMessage(Long id);

    ResponseEntity<Void> deleteMessage(Long id);

    ResponseEntity<Void> banUser(Long id);
}