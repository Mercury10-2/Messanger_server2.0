package server_2.messanger.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import server_2.messanger.domain.users.Role;
import server_2.messanger.domain.users.Status;
import server_2.messanger.domain.users.User;

import java.util.ArrayList;
import java.util.List;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                            user.getId(),
                            user.getName(),
                            user.getPassword(),
                            user.getGender().name(),
                            mapToGrantedAuthorities(user.getRole()),
                            user.getStatus().equals(Status.ACTIVE),
                            user.getLastVisit(),
                            user.getEmail()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role role) {
        List<GrantedAuthority> roles = new ArrayList<>();
        switch(role) {
            case USER:
                roles.add(new SimpleGrantedAuthority(role.name()));
                break;
            case MODER:
                roles.add(new SimpleGrantedAuthority(role.name()));
                roles.add(new SimpleGrantedAuthority(Role.USER.name()));
                break;
            case ADMIN:
                roles.add(new SimpleGrantedAuthority(role.name()));
                roles.add(new SimpleGrantedAuthority(Role.MODER.name()));
                roles.add(new SimpleGrantedAuthority(Role.USER.name()));
                break;
        }
        return roles;
    }
}