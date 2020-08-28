package server_2.messanger.service.users;

import org.springframework.http.ResponseEntity;

import server_2.messanger.dto.MessageDto;

public interface ModerService {
    
    ResponseEntity<MessageDto> updateMessage(Long id);

    ResponseEntity<Void> deleteMessage(Long id);

    ResponseEntity<Void> banUser(Long id);
}