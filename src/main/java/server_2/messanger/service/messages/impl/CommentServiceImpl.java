package server_2.messanger.service.messages.impl;

import org.springframework.stereotype.Service;

import server_2.messanger.domain.messages.Comment;
import server_2.messanger.domain.messages.Views;
import server_2.messanger.domain.users.User;
import server_2.messanger.dto.EventType;
import server_2.messanger.dto.ObjectType;
import server_2.messanger.repository.CommentRepository;
import server_2.messanger.service.messages.CommentService;
import server_2.messanger.util.WsSender;

import java.util.function.BiConsumer;

@Service
public class CommentServiceImpl implements CommentService {
    
    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    public CommentServiceImpl(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepository.save(comment);
        wsSender.accept(EventType.CREATE, commentFromDb);
        return commentFromDb;
    }
}
