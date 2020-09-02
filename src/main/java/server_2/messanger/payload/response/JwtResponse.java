package server_2.messanger.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class JwtResponse {
	private String token;
	private String type;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private String gender;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime created;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime lastVisit;
/*
	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}*/

	public JwtResponse(String token, Long id, String username, String email, List<String> roles,
						String gender, String status, LocalDateTime created, LocalDateTime lastVisit) {
		this.token = token;
		this.type = "Bearer";
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.gender = gender;
		this.status = status;
		this.created = created;
		this.lastVisit = lastVisit;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return this.roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastVisit() {
		return this.lastVisit;
	}

	public void setLastVisit(LocalDateTime lastVisit) {
		this.lastVisit = lastVisit;
	}

}