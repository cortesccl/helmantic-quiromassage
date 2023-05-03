package es.chiromassage.helmantic.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HelmanticUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelmanticUsersApplication.class, args);
	}

}
