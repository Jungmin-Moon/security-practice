package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@GetMapping() 
	public String showHome(@RequestParam(required = false) String login, @RequestParam(required = false) String register) {
		
		if (login != null) 
			return "redirect:/login";
		
		if (register != null) 
			return "redirect:/register";
		
		return "home";
	}
	
	
}
