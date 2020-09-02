package server_2.messanger.service.users.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.users.Role;
import server_2.messanger.domain.users.User;
import server_2.messanger.domain.users.ERole;
import server_2.messanger.domain.users.Gender;
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
    //private final BCryptPasswordEncoder passwordEncoder;            //  Старый. Заменить?
    private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;                          //  Новый.
    private final JwtUtils jwtUtils;

    public UserServiceImpl( UserRepository userRepository,
                            //BCryptPasswordEncoder passwordEncoder,
                            AuthenticationManager authenticationManager,
                            RoleRepository roleRepository,
                            PasswordEncoder encoder,
                            JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
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
        List<String> appliedRoles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, 
                                                userDetails.getId(), 
                                                userDetails.getUsername(), 
                                                userDetails.getEmail(), 
                                                appliedRoles,
                                                userDetails.getGender().toString(),
                                                userDetails.getStatus().toString(),
                                                userDetails.getCreated(),
                                                userDetails.getLastVisit()));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
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
                             encoder.encode(signUpRequest.getPassword()),
                             applyRoles(signUpRequest.getRole()),
                             applyGender(signUpRequest.getGender()));
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private Set<Role> applyRoles(Set<String> roles) {
		Set<Role> appliedRoles = new HashSet<>();
		if (roles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			appliedRoles.add(userRole);
		} else {
			roles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					appliedRoles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					appliedRoles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					appliedRoles.add(userRole);
				}
			});
        }
        return appliedRoles;
    }
    
    private Gender applyGender(String gender) {
        switch (gender) {
            case "мужчина":
                return Gender.MALE;
            default:
                return Gender.FEMALE;
        }
    }

    @Override
    public List<User> getUsers() {/*
        return getUserList().stream()
                .map(user -> getDto(user, null))
                .collect(Collectors.toList());*/
        return null;
    }

    @Override
    public User getUser(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @Override
    public void deleteUser() {
        // TODO Auto-generated method stub
    }

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
        Set<Role> roles = new HashSet<>();
		if (userRepository.findAll().isEmpty()) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
            User user = new User("Аня", "ann@mail.ru", "333333", new HashSet<Role>(roles), Gender.FEMALE);
			userRepository.save(user);

            roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			user = new User("Катя", "kate@mail.ru", "333333", new HashSet<Role>(roles), Gender.FEMALE);
			userRepository.save(user);

            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			user = new User("Оля", "olya@mail.ru", "333333", new HashSet<Role>(roles), Gender.FEMALE);
			userRepository.save(user);
        }
        return userRepository.findAll();
	}    
}