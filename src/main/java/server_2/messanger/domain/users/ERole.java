package server_2.messanger.domain.users;

public enum ERole {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN;

    @Override
    public String toString() {
        switch(this) {
            case ROLE_ADMIN: return "администратор";
            case ROLE_MODERATOR: return "модератор";
            default: return "пользователь";
        }
    }
}