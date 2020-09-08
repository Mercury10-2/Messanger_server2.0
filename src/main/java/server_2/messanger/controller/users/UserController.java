package server_2.messanger.controller.users;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server_2.messanger.domain.users.User;
import server_2.messanger.payload.response.UserDto;
import server_2.messanger.service.users.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = { "http://localhost:8081" })
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("{name}")
    public User getUser(@PathVariable(name = "name") String name) {
        return service.getUser(name);
    }

    @DeleteMapping("/auth/admin")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteUser() {
        service.deleteUser();
    }
}