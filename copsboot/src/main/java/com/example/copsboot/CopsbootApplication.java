package com.example.copsboot;

import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.UUID;

@SpringBootApplication
public class CopsbootApplication {

	@Bean
	public UniqueIdGenerator<UUID> uniqueIdGenerator() {
		return new InMemoryUniqueIdGenerator();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}@Bean

	public AuthenticationManager authenticationManager() {
		return new OAuth2AuthenticationManager();
	}

	@Bean
	public TokenStore tokenStore() {
		// We use the InMemoryTokenStore to get started. Later on, we will use the JdbcTokenStore to store the tokens
		//in the database
		return new InMemoryTokenStore();
	}

	public static void main(String[] args) {
		SpringApplication.run(CopsbootApplication.class, args);
	}
}
