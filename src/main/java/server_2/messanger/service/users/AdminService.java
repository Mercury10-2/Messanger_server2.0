package server_2.messanger.service.users;

import org.springframework.http.ResponseEntity;

import server_2.messanger.domain.users.User;

public interface AdminService {
    
    ResponseEntity<User> changeRole(Long id);
}