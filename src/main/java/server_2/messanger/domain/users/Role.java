package server_2.messanger.domain.users;

public enum Role {
    USER, MODER, ADMIN;

    @Override
    public String toString() {
        switch(this) {
            case USER: return "пользователь";
            case MODER: return "модератор";
            default: return "администратор";
        }
    }
}