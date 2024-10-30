package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String registerPage(@RequestParam(required = false) String home) {
		if (home != null) 
			return "redirect:/home";
		
		return "register";
	}
	
	@PostMapping()
	public String registerPost(User user, Model model) {
		
		if (userService.createUser(user) == false) {
			return "redirect:/home";
		} else {
			String message = "The username " + user.getUsername() + " was already taken. Please use a different one.";
			model.addAttribute("error_message",message);		
			return "register";
		}
		
		//return "redirect:/home";
	}
}
