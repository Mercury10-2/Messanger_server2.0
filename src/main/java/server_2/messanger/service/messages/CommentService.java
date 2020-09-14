package server_2.messanger.service.messages;

import server_2.messanger.domain.messages.Comment;
import server_2.messanger.domain.users.User;

public interface CommentService {
    
    Comment create(Comment comment, User user);
}
