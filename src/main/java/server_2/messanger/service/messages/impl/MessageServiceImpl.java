package server_2.messanger.service.messages.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.repository.MessageRepository;
import server_2.messanger.service.messages.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessage(Long id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public Message create(Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public Message update(Long id, Message message) {
        Message messageFromDb = getMessage(id);
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepository.save(messageFromDb);
    }

    @Override
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
}