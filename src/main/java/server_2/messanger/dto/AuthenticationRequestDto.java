package server_2.messanger.dto;

public class AuthenticationRequestDto {
    
    private String name;
    private String password;

    public AuthenticationRequestDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}