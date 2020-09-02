package server_2.messanger.service.users.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.service.users.ModerService;

@Service
public class ModerServiceImpl implements ModerService {

    @Override
    public ResponseEntity<Message> updateMessage(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteMessage(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> banUser(Long id) {
        return null;
    }
    
}