package server_2.messanger.controller.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server_2.messanger.dto.MessageDto;
import server_2.messanger.service.users.impl.ModerServiceImpl;

@RestController
@RequestMapping("users/moder")
@CrossOrigin(origins = { "http://localhost:8081" })
public class ModerController {
    
    private final ModerServiceImpl service;

    public ModerController(ModerServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable(name = "id") Long id) {
        return service.updateMessage(id);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable(name = "id") Long id) {
        return service.deleteMessage(id);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> banUser(@PathVariable(name = "id") Long id) {
        return service.banUser(id);
    }
}