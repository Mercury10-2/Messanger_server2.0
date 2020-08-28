package server_2.messanger.controller.users;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server_2.messanger.dto.UserDto;
import server_2.messanger.service.users.impl.UserServiceImpl;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = { "http://localhost:8081" })
public class UserController {

    private final UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
        service.generateUsers();
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("{name}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "name") String name) {  //  HttpStatus.NO-CONTENT (203 error)??
        return service.getUser(name);
    }

    @GetMapping("{name}/{password}")
    public ResponseEntity<UserDto> login(  @PathVariable(name = "name") String name,
                                                    @PathVariable(name = "password") String password) {
        return service.login(name, password);
    }
    
    @PostMapping("{name}/{password}/{gender}/{email}")                  //  Попробовать map извлекать из Json?
    public ResponseEntity<UserDto> register(@PathVariable(name = "name") String name,
                                                @PathVariable(name = "password") String password,
                                                @PathVariable(name = "gender") String gender,
                                                @PathVariable(name = "email") String email) {
        return service.register(name, password, gender, email);
    }
}