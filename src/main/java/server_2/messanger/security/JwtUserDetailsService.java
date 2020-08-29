package server_2.messanger.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.users.User;
import server_2.messanger.security.jwt.JwtUser;
import server_2.messanger.security.jwt.JwtUserFactory;
import server_2.messanger.service.users.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService service;

    public JwtUserDetailsService(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.getUser(username);
        if (user == null)
            throw new UsernameNotFoundException("User with name: " + username + " not found");
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}