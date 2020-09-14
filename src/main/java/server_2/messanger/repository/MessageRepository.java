package server_2.messanger.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.domain.users.User;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = { "comments" })
    Page<Message> findByAuthorIn(List<User> users, Pageable pageable);
}