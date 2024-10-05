package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	
	@GetMapping() 
	public String loginPage() {
		return "login";
	}
	
	/*
	@PostMapping()
	public String loginSuccess(@RequestParam String username, @RequestParam String password) {
		
		
		
		return "redirect:/";
	} */
}