package server_2.messanger.domain.users;

public enum Role {
    USER, MODER, ADMIN;

    @Override
    public String toString() {
        switch(this) {
            case MODER: return "модератор";
            case ADMIN: return "администратор";
            default: return "пользователь";
        }
    }
}