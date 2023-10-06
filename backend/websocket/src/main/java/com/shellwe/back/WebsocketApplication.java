package com.shellwe.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WebsocketApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

}
