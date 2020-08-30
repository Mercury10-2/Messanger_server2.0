package server_2.messanger.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String name;
    private final String password;
    private final String gender;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final LocalDateTime lastVisit;
    private final String email;

    public JwtUser(Long id, String name, String password, String gender, Collection<? extends GrantedAuthority> authorities,
                boolean enabled, LocalDateTime lastVisit, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastVisit = lastVisit;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public String getGender() {
        return gender;
    }
}