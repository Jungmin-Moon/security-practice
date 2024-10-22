package com.example.demo.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Transactions;
import com.example.demo.repositories.BankAccountRepository;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	BankAccountRepository bankRepo;
	
	@GetMapping() 
	public String profileUser(Authentication auth, Model model, @RequestParam(required = false) String transactions) {
		
		if (transactions != null) {
			return "redirect:/profile/transactions";
		}
		
		//create a DTO object for bank accounts for easier manipulation
		//then use it
		
		UserDetails user = (UserDetails) auth.getPrincipal();
		
		//System.out.println(user.getUsername());
		
		var accountBankInfo = bankRepo.getInfo(user.getUsername());
		
		//due to display need to either limit max in an account or use BigDecimal and be able to store those in the 
		model.addAttribute("bankInfo", accountBankInfo);
		
		return "profile.html";
	}
	
	
	
	@GetMapping("/transactions")
	public String transactions(Authentication auth, Model model) {
		//transactionType is always passed, and so is transactionAmount
		//the target account is the one that will sometimes be there
		UserDetails user = (UserDetails) auth.getPrincipal();
		
		
		
		return "transactions.html";
	}
	
	@PostMapping("/transactions")
	public String transactionsPost(@RequestParam(required = false) String targetAccount, @RequestParam String transactionType, @RequestParam String transactionAmount,
									Authentication auth) {
		UserDetails user = (UserDetails) auth.getPrincipal();
		
		if (targetAccount != null) {
			
		}
		
		Transactions transaction = createTransactionWithdrawAndDeposit(transactionAmount, transactionType);
		
		return "redirect:/profile";
	}
	
	
	
	private Transactions createTransactionWithdrawAndDeposit(String transactionAmount, String transactionType) {
		
		Transactions transaction = new Transactions();
		
		BigDecimal amount = new BigDecimal(Integer.parseInt(transactionAmount));
		
		transaction.setTransactionAmount(amount);
		transaction.setTransactionType(transactionType);
		
		return transaction;
	}
	
	private Transactions createTransferTransaction(String targetAccount, String transactionAmount, String transactionType) {
		Transactions transferTransaction = new Transactions();
		
		BigDecimal amount = new BigDecimal(Integer.parseInt(transactionAmount));
		
		transferTransaction.setTransactionAmount(amount);
		transferTransaction.setTransactionType(transactionType);
		
		return transferTransaction;
	}
}
