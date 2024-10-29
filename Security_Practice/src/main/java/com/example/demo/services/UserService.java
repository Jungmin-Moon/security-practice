package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.CustomUserDetails;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BankService bankService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.getUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user.");
		}
		
		return new CustomUserDetails(user);
	}
	
	public boolean createUser(User user) {
		boolean created = true;
		
		String checkUsername = user.getUsername();
		
		if (userRepo.getUserByUsername(checkUsername) == null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			
			user.setPassword(encodedPassword);
			
			user.setRole("USER");
			
			userRepo.save(user);
			bankService.createBankAccount(user);
		} else {
			created = false;
		}
		
		return created;
	}
}
