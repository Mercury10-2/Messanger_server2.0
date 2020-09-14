package server_2.messanger.controller.users;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server_2.messanger.domain.users.User;
import server_2.messanger.payload.request.LoginRequest;
import server_2.messanger.payload.request.SignupRequest;
import server_2.messanger.service.users.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600)
public class AuthenticationController {

	private final UserService service;

	public AuthenticationController(UserService service) {
		this.service = service;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return service.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		return service.registerUser(signupRequest);
	}

	//	Test
	@GetMapping
	public List<User> getAll() {
		service.generateRoles();
		return service.generateUsers();
	}
}