package server_2.messanger.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.users.Role;
import server_2.messanger.domain.users.Status;
import server_2.messanger.domain.users.User;
import server_2.messanger.domain.users.Gender;
import server_2.messanger.dto.UserDto;
import server_2.messanger.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserDto> getUser(String name) {
        return getResponseDto(getUserFromDb(name));
    }

    public List<UserDto> getUsers() {
        return getUserList().stream()
                .map(user -> getDto(user))
                .collect(Collectors.toList());
    }

    public ResponseEntity<UserDto> verifyPassword(String name, String password) {
        User user = getUserFromDb(name);
        if (user == null)
            return sendError("name");
        if (!user.getPassword().equals(password))
            return sendError("password");
        return getResponseDto(user);
    }

    public ResponseEntity<UserDto> registration(String name, String password, String gender, String email) {
        User user = getUserFromDb(name);
        if (user != null)
            return sendError("exists");
        else {
            user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setGender(gender.equals("мужчина") ? Gender.MALE : Gender.FEMALE);
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            user.setCreated(LocalDateTime.now());
            user.setLastVisit(LocalDateTime.now());
            user.setEmail(email);
            return getResponseDto(userRepository.save(user));
        }
    }

    private ResponseEntity<UserDto> getResponseDto(User user) {
        return new ResponseEntity<UserDto>(getDto(user), HttpStatus.OK);        //  Попробовать иначе ResponseEntity создавать?
    }

    private UserDto getDto(User user) {
        return new UserDto(
                            user.getId(),        
                            user.getName(),
                            user.getGender(),
                            user.getRole(),
                            user.getStatus(),
                            user.getLastVisit(),
                            user.getCreated(),
                            user.getEmail());
    }

    private ResponseEntity<UserDto> sendError(String error) {
        return new ResponseEntity<UserDto>(new UserDto(error), HttpStatus.OK);
    }

    private User getUserFromDb(String name) {
        return userRepository.findByName(name);
    }

    private List<User> getUserList() {
        return userRepository.findAll();
    }

    public void generateUsers() {
        if (getUsers().isEmpty()) {
            User user = new User();
            user.setName("Юля");
            user.setPassword("Юля");
            user.setGender(Gender.FEMALE);
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            user.setLastVisit(LocalDateTime.now());
            user.setCreated(LocalDateTime.now());
            user.setEmail("julia@mail.ru");
            userRepository.save(user);

            user = new User();
            user.setName("Женя");
            user.setPassword("Женя");
            user.setGender(Gender.FEMALE);
            user.setRole(Role.MODER);
            user.setStatus(Status.ACTIVE);
            user.setLastVisit(LocalDateTime.now());
            user.setCreated(LocalDateTime.now());
            user.setEmail("eugene@mail.ru");
            userRepository.save(user);

            user = new User();
            user.setName("Аня");
            user.setPassword("Аня");
            user.setGender(Gender.FEMALE);
            user.setRole(Role.ADMIN);
            user.setStatus(Status.ACTIVE);
            user.setLastVisit(LocalDateTime.now());
            user.setCreated(LocalDateTime.now());
            user.setEmail("ann@mail.ru");
            userRepository.save(user);
        }
    }
}