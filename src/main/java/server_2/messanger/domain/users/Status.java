package server_2.messanger.domain.users;

public enum Status {
    ACTIVE, NOT_ACTIVE, DELETED;

    @Override
    public String toString() {
        switch(this) {
            case ACTIVE: return "активен";
            case NOT_ACTIVE: return "неактивен";
            default: return "удалён";
        }
    }
}