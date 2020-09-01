package server_2.messanger.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import server_2.messanger.domain.users.Gender;
import server_2.messanger.domain.users.Role;
import server_2.messanger.domain.users.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {/*

    private Long id;
    private String name;
    private String gender;
    private String role;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime lastVisit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime created;
    private String email;
    private String token;

    public UserDto() {
    }

    public UserDto(Long id, String name, Gender gender, Role role, Status status,
                    LocalDateTime lastVisit, LocalDateTime created, String email, String token) {
        this.id = id;
        this.name = name;
        this.gender = gender.name();
        this.role = role.name();
        this.status = status.name();
        this.lastVisit = lastVisit;
        this.created = created;
        this.email = email;
        this.token = token;
    }

    public UserDto(String error) {
        this.token = "error";
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastVisit() {
        return this.lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }*/
}