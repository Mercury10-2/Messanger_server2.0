package server_2.messanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server_2.messanger.domain.users.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}