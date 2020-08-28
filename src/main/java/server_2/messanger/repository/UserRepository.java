package server_2.messanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server_2.messanger.domain.users.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByname(String name);
}