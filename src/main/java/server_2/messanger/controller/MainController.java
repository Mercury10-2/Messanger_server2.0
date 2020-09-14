package server_2.messanger.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import server_2.messanger.controller.messages.MessageController;
import server_2.messanger.domain.messages.Views;
import server_2.messanger.domain.users.User;
import server_2.messanger.dto.MessagePageDto;
import server_2.messanger.repository.UserRepository;
import server_2.messanger.service.messages.MessageService;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    @Value("${spring.profiles.active:prod}")
    private String profile;
    
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;

    public MainController(MessageService messageService, UserRepository userRepository, ObjectMapper mapper) {
        this.messageService = messageService;
        this.userRepository = userRepository;
        ObjectMapper objectMapper = mapper.setConfig(mapper.getSerializationConfig());
        this.messageWriter = objectMapper.writerWithView(Views.FullMessage.class);
        this.profileWriter = objectMapper.writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        if (user != null) {
            User userFromDb = userRepository.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedProfile);
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findForUser(pageRequest, user);
            String messages = messageWriter.writeValueAsString(messagePageDto.getMessages());
            model.addAttribute("messages", messages);
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());
        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
