package com.example.demo.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.LoginSuccessHandler;
import com.example.demo.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
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
		
		http.authorizeHttpRequests(a -> a.requestMatchers("/","/home", "/login", "/register").permitAll()
										.requestMatchers("/profile","/profile/**").hasRole("USER")
										.requestMatchers("/admin", "/admin/**").hasRole("ADMIN").anyRequest().authenticated());
		
		http.formLogin(l -> l.loginPage("/login").successHandler(loginSuccessHandler));
		http.logout((logout) -> logout.permitAll());
		
		return http.build();
	}
	
}
