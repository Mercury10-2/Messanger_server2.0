package server_2.messanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server_2.messanger.domain.messages.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
