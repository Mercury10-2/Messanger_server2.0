package server_2.messanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Messanger {

	public static void main(String[] args) {
		SpringApplication.run(Messanger.class, args);
	}
}
/*
UserDto - заменить на JwtResponse с JsonView ??
Role - наследовать от BaseEntity или разбить BaseEntity на два класса (id и created)?
Sentry - надо?
*/