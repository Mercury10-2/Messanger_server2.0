package server_2.messanger.controller.messages;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.domain.messages.Views;
import server_2.messanger.domain.users.User;
import server_2.messanger.dto.MessagePageDto;
import server_2.messanger.service.messages.MessageService;

import java.io.IOException;

@RestController
@RequestMapping("messages")
@CrossOrigin(origins = { "http://localhost:8081" })
public class MessageController {

    public static final int MESSAGES_PER_PAGE = 3;
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }
/*
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
    }*/

    //  --------------------    letscode    ----------------------

    //  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")         - Использовать?

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(@AuthenticationPrincipal User user,
                               @PageableDefault(
                                    size = MESSAGES_PER_PAGE,
                                    sort = { "id" },
                                    direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    @JsonView(Views.FullMessage.class)
    public Message create(@RequestBody Message message, @AuthenticationPrincipal User user) throws IOException {
        return service.create(message, user);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) throws IOException {
        return service.update(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        service.delete(message);
    }
}