package server_2.messanger.domain.users;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import server_2.messanger.domain.BaseEntity;
import server_2.messanger.domain.messages.UserSubscription;
import server_2.messanger.domain.messages.Views;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
			                                @UniqueConstraint(columnNames = "email")})
public class User extends BaseEntity {

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
    private String password;
    
    @JsonView(Views.IdName.class)
    private String userpic;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", 
			   joinColumns = @JoinColumn(name = "user_id"), 
			   inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @JsonView(Views.FullProfile.class)
    private String locale;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "subscriber", orphanRemoval = true)
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserSubscription> subscribers = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime lastVisit;

	public User() {}

    public User(String username, String email, String password, Set<Role> roles, Gender gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.gender = gender;
        this.status = Status.ACTIVE;
        setCreated(LocalDateTime.now());
        this.lastVisit = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "{ id='" + getId() + "', username='" + getUsername() + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        User other = (User) o;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserpic() {
        return this.userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<UserSubscription> getSubscriptions() {
        return this.subscriptions;
    }

    public void setSubscriptions(Set<UserSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<UserSubscription> getSubscribers() {
        return this.subscribers;
    }

    public void setSubscribers(Set<UserSubscription> subscribers) {
        this.subscribers = subscribers;
    }

    public LocalDateTime getLastVisit() {
        return this.lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

}
