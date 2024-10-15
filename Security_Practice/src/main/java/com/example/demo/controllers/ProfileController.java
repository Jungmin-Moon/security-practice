package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repositories.BankAccountRepository;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	BankAccountRepository bankRepo;
	
	@GetMapping() 
	public String profileUser(Authentication auth, Model model) {
		
		//create a DTO object for bank accounts for easier manipulation
		//then use it
		
		UserDetails user = (UserDetails) auth.getPrincipal();
		
		System.out.println(user.getUsername());
		
		var accountBankInfo = bankRepo.getInfo(user.getUsername());
		
		model.addAttribute("bankInfo", accountBankInfo);
		
		//System.out.println(accountBankInfo.getAccountId() + " " + accountBankInfo.getAmount() + " " + accountBankInfo.getUsername());
		
		return "profile.html";
	}
}
