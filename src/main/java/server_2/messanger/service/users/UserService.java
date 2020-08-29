package server_2.messanger.service.users;

import java.util.List;

import org.springframework.http.ResponseEntity;

import server_2.messanger.domain.users.User;
import server_2.messanger.dto.UserDto;

public interface UserService {
    
    User getUser(String name);

    ResponseEntity<UserDto> getUserDto(String name);

    List<UserDto> getUsers();

    ResponseEntity<UserDto> login(String name, String password);

    ResponseEntity<UserDto> register(String name, String password, String gender, String email);
}