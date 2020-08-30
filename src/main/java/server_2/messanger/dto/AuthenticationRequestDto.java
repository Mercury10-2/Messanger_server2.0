package server_2.messanger.dto;

public class AuthenticationRequestDto {
    
    private String name;
    private String password;

    public AuthenticationRequestDto() {
    }

    public AuthenticationRequestDto(String name, String password) {
        this.name = name;
        this.password = password;
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