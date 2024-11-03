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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.example.demo.security.LoginSuccessHandler;
import com.example.demo.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer{
	
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
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/static/**")
    			.addResourceLocations("classpath:/static/")
    			.setCachePeriod(3900)
    			.resourceChain(true)
    			.addResolver(new PathResourceResolver());
    } 
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    	return (web) -> web.ignoring().requestMatchers("/css/**");
    }
	
	@Bean
	SecurityFilterChain config(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
		
		http.authorizeHttpRequests(a -> a.requestMatchers("/","/home", "/login", "/register", "/bankaccounts/**").permitAll()
										.requestMatchers("/profile/**").hasAnyRole("USER", "ADMIN")
										.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated());
		
		http.formLogin(l -> l.loginPage("/login").successHandler(loginSuccessHandler));
		http.logout((logout) -> logout.permitAll());
		
		return http.build();
	}
	
}
