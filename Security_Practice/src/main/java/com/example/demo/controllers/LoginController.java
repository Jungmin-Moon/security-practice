package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	
	@GetMapping() 
	public String loginPage(@RequestParam(required = false) String home) {
		
		if(home != null) {
			return "redirect:/home";
		}
		
		return "login";
	}
}
