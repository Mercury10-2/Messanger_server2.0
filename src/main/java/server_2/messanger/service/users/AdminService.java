package server_2.messanger.service.users;

import org.springframework.http.ResponseEntity;

import server_2.messanger.dto.UserDto;

public interface AdminService {
    
    ResponseEntity<UserDto> changeRole(Long id);
}