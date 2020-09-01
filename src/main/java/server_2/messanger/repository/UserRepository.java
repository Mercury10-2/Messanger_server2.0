package server_2.messanger.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import server_2.messanger.domain.users.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //Optional<User> findByName(String name);

    Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}