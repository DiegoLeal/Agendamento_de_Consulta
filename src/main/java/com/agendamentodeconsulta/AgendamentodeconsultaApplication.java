package com.agendamentodeconsulta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class AgendamentodeconsultaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendamentodeconsultaApplication.class, args);
	}

	@Bean
	public PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

}
