package com.example.demo.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import com.example.demo.services.TransactionService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	BankAccountRepository bankRepo;
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping() 
	public String profileUser(Authentication auth, Model model, @RequestParam(required = false) String transactions) {
		
		if (transactions != null) {
			return "redirect:/profile/transactions";
		}
		
		UserDetails user = (UserDetails) auth.getPrincipal();
		
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
		Transactions transaction = new Transactions();
		transaction.setUsername(user.getUsername());
		
		if (targetAccount != null) {
			transaction = createTransferTransaction(targetAccount, transactionAmount, transactionType);
		} else {
		
			transaction = createTransactionWithdrawAndDeposit(transactionAmount, transactionType);
		}
		
		//need to get the username for the account making the transaction and send that as well to modify
		
		transactionService.addTransaction(transaction);
		
		
		return "redirect:/profile";
	}
	
	
	
	private Transactions createTransactionWithdrawAndDeposit(String transactionAmount, String transactionType) {
		
		Transactions transaction = new Transactions();
		
		BigDecimal amount = new BigDecimal(Integer.parseInt(transactionAmount));
		
		transaction.setTransactionAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setTransactionDate(LocalDate.now());
		
		return transaction;
	}
	
	private Transactions createTransferTransaction(String targetAccount, String transactionAmount, String transactionType) {
		Transactions transferTransaction = new Transactions();
		
		
		BigDecimal amount = new BigDecimal(Integer.parseInt(transactionAmount));
		
		transferTransaction.setTransactionAmount(amount);
		transferTransaction.setTransactionType(transactionType);
		transferTransaction.setTransactionDate(LocalDate.now());
		
		return transferTransaction;
	}
}
