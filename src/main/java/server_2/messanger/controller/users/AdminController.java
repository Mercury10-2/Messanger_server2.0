package server_2.messanger.controller.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server_2.messanger.dto.UserDto;
import server_2.messanger.service.users.impl.AdminServiceImpl;

@RestController
@RequestMapping("users/admin")
@CrossOrigin(origins = { "http://localhost:8081" })
public class AdminController {
    
    private final AdminServiceImpl service;

    public AdminController(AdminServiceImpl service) {
        this.service = service;
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto> changeRole(@PathVariable(name = "id") Long id) {
        return service.changeRole(id);
    }
}