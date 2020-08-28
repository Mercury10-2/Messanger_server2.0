package server_2.messanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server_2.messanger.domain.messages.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}