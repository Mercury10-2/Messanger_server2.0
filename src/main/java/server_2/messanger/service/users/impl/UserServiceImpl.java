package server_2.messanger.service.users.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.users.Role;
import server_2.messanger.domain.users.Status;
import server_2.messanger.domain.users.User;
import server_2.messanger.domain.users.ERole;
import server_2.messanger.domain.users.Gender;
import server_2.messanger.dto.UserDto;
import server_2.messanger.payload.request.LoginRequest;
import server_2.messanger.payload.request.SignupRequest;
import server_2.messanger.payload.response.JwtResponse;
import server_2.messanger.payload.response.MessageResponse;
import server_2.messanger.repository.RoleRepository;
import server_2.messanger.repository.UserRepository;
import server_2.messanger.security.jwt.JwtUtils;
import server_2.messanger.security.services.UserDetailsImpl;
import server_2.messanger.service.users.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;            //  Старый. Заменить?
    private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;                          //  Новый.
    private final JwtUtils jwtUtils;

    public UserServiceImpl( UserRepository userRepository,
                            BCryptPasswordEncoder passwordEncoder,
                            AuthenticationManager authenticationManager,
                            RoleRepository roleRepository,
                            PasswordEncoder encoder,
                            JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
    
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, 
                                                userDetails.getId(), 
                                                userDetails.getUsername(), 
                                                userDetails.getEmail(), 
                                                roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        generateUsers();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

/*
    @Override
    public User getUser(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @Override
    public UserDto getDto(User user, String token) {
        return new UserDto(user.getId(), user.getName(), user.getGender(), user.getRole(), user.getStatus(),
                            user.getLastVisit(), user.getCreated(), user.getEmail(), token);
    }

    @Override
    public ResponseEntity<UserDto> getUserDto(String name) {
        return getResponseDto(getUser(name));
    }

    @Override
    public List<UserDto> getUsers() {
        return getUserList().stream()
                .map(user -> getDto(user, null))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<UserDto> login(String name, String password) {
        User user = getUser(name);
        if (user == null)
            return sendError("name");
        if (!user.getPassword().equals(password))
            return sendError("password");
        return getResponseDto(user);
    }

    @Override
    public UserDto getError(String error) {
        return new UserDto(error);
    }

    @Override
    public ResponseEntity<UserDto> register(String name, String password, String gender, String email) {
        User user = getUser(name);
        if (user != null)
            return sendError("exists");
        else {
            user = new User();
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setGender(gender.equals("мужчина") ? Gender.MALE : Gender.FEMALE);
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            user.setCreated(LocalDateTime.now());
            user.setLastVisit(LocalDateTime.now());
            user.setEmail(email);
            return getResponseDto(userRepository.save(user));
        }
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    private ResponseEntity<UserDto> getResponseDto(User user) {
        return new ResponseEntity<UserDto>(getDto(user, null), HttpStatus.OK);
    }

    private ResponseEntity<UserDto> sendError(String error) {
        return new ResponseEntity<UserDto>(new UserDto(error), HttpStatus.OK);
    }

    private List<User> getUserList() {
        return userRepository.findAll();
    }

    public void generateUsers() {
        if (getUsers().isEmpty()) {
            User user = new User();
            user.setName("Юля");
            user.setPassword(new BCryptPasswordEncoder().encode("Юля"));
            user.setGender(Gender.FEMALE);
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            user.setLastVisit(LocalDateTime.now());
            user.setCreated(LocalDateTime.now());
            user.setEmail("julia@mail.ru");
            userRepository.save(user);

            user = new User();
            user.setName("Женя");
            user.setPassword(new BCryptPasswordEncoder().encode("Женя"));
            user.setGender(Gender.FEMALE);
            user.setRole(Role.MODER);
            user.setStatus(Status.ACTIVE);
            user.setLastVisit(LocalDateTime.now());
            user.setCreated(LocalDateTime.now());
            user.setEmail("eugene@mail.ru");
            userRepository.save(user);

            user = new User();
            user.setName("Аня");
            user.setPassword(new BCryptPasswordEncoder().encode("Аня"));
            user.setGender(Gender.FEMALE);
            user.setRole(Role.ADMIN);
            user.setStatus(Status.ACTIVE);
            user.setLastVisit(LocalDateTime.now());
            user.setCreated(LocalDateTime.now());
            user.setEmail("ann@mail.ru");
            userRepository.save(user);
        }
    }*/

    @Override
	public void generateRoles() {
		if (roleRepository.findAll().isEmpty()) {
			roleRepository.save(new Role(ERole.ROLE_ADMIN));
			roleRepository.save(new Role(ERole.ROLE_MODERATOR));
			roleRepository.save(new Role(ERole.ROLE_USER));
		}
	}

    @Override
	public List<User> generateUsers() {
		if (userRepository.findAll().isEmpty()) {
			User user = new User("Аня", "ann@mail.ru", "333333");
			user.getRoles().add(roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			userRepository.save(user);

			user = new User("Катя", "kate@mail.ru", "333333");
			user.getRoles().add(roleRepository.findByName(ERole.ROLE_MODERATOR)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			userRepository.save(user);

			user = new User("Оля", "olya@mail.ru", "333333");
			user.getRoles().add(roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			userRepository.save(user);
        }
        return userRepository.findAll();
	}

@Override
public UserDto getDto(User user, String token) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public UserDto getError(String name) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public ResponseEntity<UserDto> getUserDto(String name) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public List<UserDto> getUsers() {
    // TODO Auto-generated method stub
    return null;
}

@Override
public ResponseEntity<UserDto> login(String name, String password) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public ResponseEntity<UserDto> register(String name, String password, String gender, String email) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public void deleteAllUsers() {
    // TODO Auto-generated method stub

}

@Override
public User getUser(String name) {
    // TODO Auto-generated method stub
    return null;
}
    
}