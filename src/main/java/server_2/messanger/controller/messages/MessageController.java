package server_2.messanger.controller.messages;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.domain.messages.Views;
import server_2.messanger.service.messages.MessageService;

import java.util.List;

@RestController
@RequestMapping("messages")
@CrossOrigin(origins = { "http://localhost:8081" })
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> getMessages() {
        return service.getMessages();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Long id) {
        return service.getMessage(id);
    }

    @PostMapping("/auth")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Message create(@RequestBody Message message) {
        return service.create(message);
    }

    @PutMapping("/auth/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Message update(@PathVariable("id") Long id, @RequestBody Message message) {
        return service.update(id, message);
    }

    @DeleteMapping("/auth/mod/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}