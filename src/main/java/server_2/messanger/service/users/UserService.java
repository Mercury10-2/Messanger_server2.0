package server_2.messanger.service.users;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import server_2.messanger.domain.users.User;
import server_2.messanger.dto.UserDto;
import server_2.messanger.payload.request.LoginRequest;
import server_2.messanger.payload.request.SignupRequest;

public interface UserService {
    
    //  Нужен ??
    User getUser(String name);

    UserDto getDto(User user, String token);

    UserDto getError(String name);

    //  Нужен ??
    ResponseEntity<UserDto> getUserDto(String name);

    List<UserDto> getUsers();

    //  Нужен ??
    ResponseEntity<UserDto> login(String name, String password);

    ResponseEntity<UserDto> register(String name, String password, String gender, String email);

    void deleteAllUsers();

    //-----------------------------

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signUpRequest);

    //  Test
	void generateRoles();

    //  Test
	List<User> generateUsers();

}