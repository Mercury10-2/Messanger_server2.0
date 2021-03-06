package server_2.messanger.service.users;

import java.util.List;

import org.springframework.http.ResponseEntity;

import server_2.messanger.domain.users.User;
import server_2.messanger.payload.request.LoginRequest;
import server_2.messanger.payload.request.SignupRequest;
import server_2.messanger.payload.response.UserDto;

public interface UserService {
    
    List<UserDto> getUsers();

    User getUser(String name);

    void deleteUser();

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signUpRequest);

    //-----------    Test   ----------------

	void generateRoles();

    List<User> generateUsers();
}