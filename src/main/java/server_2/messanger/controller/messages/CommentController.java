package server_2.messanger.controller.messages;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server_2.messanger.domain.messages.Comment;
import server_2.messanger.domain.messages.Views;
import server_2.messanger.domain.users.User;
import server_2.messanger.service.messages.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {
    
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(@RequestBody Comment comment, @AuthenticationPrincipal User user) {
        return service.create(comment, user);
    }
}
