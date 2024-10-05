package com.example.demo.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserService();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	SecurityFilterChain config(HttpSecurity http) throws Exception {
		//only for now while testing
		http.csrf(csrf -> csrf.disable());
		
		http.authenticationProvider(authenticationProvider());
		
		http.authorizeHttpRequests(a -> a.requestMatchers("/home","/login", "/register").permitAll());
		
		http.authorizeHttpRequests(a -> a.requestMatchers("/profile").hasAnyRole("USER"));
		http.authorizeHttpRequests(a -> a.requestMatchers("/admin").hasAnyRole("ADMIN"));
		
		http.formLogin(l -> l.loginPage("/login").permitAll()).logout((logout) -> logout.permitAll());
		
		
		
		return http.build();
	}
}
