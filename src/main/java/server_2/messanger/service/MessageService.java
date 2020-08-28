package server_2.messanger.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessage(Long id) {
        return messageRepository.findById(id).get();
    }

    public Message create(Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public Message update(Long id, Message message) {
        Message messageFromDb = getMessage(id);
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepository.save(messageFromDb);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
}