package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	private UserService userService;
	
	public RegisterController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping() 
	public String registerPage() {
		return "register";
	}
	
	@PostMapping()
	public String registerPost(@RequestParam(required = false) String home, User user) {
		
		if (home != null) 
			return "redirect:/home";
		
		userService.createUser(user);
		
		return "redirect:/home";
	}
}