package server_2.messanger.domain.users;

public enum Gender {
    MALE, FEMALE;

    @Override
    public String toString() {
        switch(this) {
            case MALE: return "мужчина";
            default: return "женщина";
        }
    }
}