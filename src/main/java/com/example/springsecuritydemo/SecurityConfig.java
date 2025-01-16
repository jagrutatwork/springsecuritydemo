package com.example.springsecuritydemo;
import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	DataSource dataSource;
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> 
		requests
		.requestMatchers("register","login")
		.permitAll()
		.anyRequest().authenticated());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
//		http.headers(headers ->
//				headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); 
		http.csrf(csrf -> csrf.disable());
		return http.build();
	}
	
	
	@Bean
	UserDetailsService userDetailsService() {
		UserDetails user1 = User.withUsername("user1").
				password("{noop}password1")
				.roles("USER")
				.build();
		
		UserDetails admin = User.withUsername("admin").
				password("{noop}password2")
				.roles("ADMIN")
				.build();
		
//		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//		userDetailsManager.createUser(user1);
//		userDetailsManager.createUser(admin);
//		return  userDetailsManager;
		return new InMemoryUserDetailsManager(user1, admin);
	}
	
	@SuppressAjWarnings("deprecation")
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
		
		
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
}
