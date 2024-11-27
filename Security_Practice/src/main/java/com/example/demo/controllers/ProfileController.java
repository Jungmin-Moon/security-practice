package com.example.demo.controllers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;

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
import com.example.demo.services.TransactionCheckService;
import com.example.demo.services.TransactionService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	private BankAccountRepository bankRepo;
	private TransactionService transactionService;
	private TransactionCheckService transactionCheckService;
	
	ProfileController(BankAccountRepository bankRepo, TransactionService transactionService, TransactionCheckService transactionCheckService) {
		this.bankRepo = bankRepo;
		this.transactionService = transactionService;
		this.transactionCheckService = transactionCheckService;
	}
	
	@GetMapping() 
	public String profileUser(Authentication auth, Model model, @RequestParam(required = false) String transactions) {
		
		if (transactions != null) {
			return "redirect:/profile/transactions";
		}
		
		UserDetails user = (UserDetails) auth.getPrincipal();
		
		var accountBankInfo = bankRepo.getInfo(user.getUsername());
		
		var userTransactions = transactionService.getTransactions(user.getUsername());
		
		model.addAttribute("bankInfo", accountBankInfo);
		model.addAttribute("userTransactions", userTransactions);
		
		return "profile.html";
	}
	
	
	
	@GetMapping("/transactions")
	public String transactions(Authentication auth, Model model, @RequestParam(required = false) String back) {
		
		if (back != null) {
			return "redirect:/profile";
		}
		
		
		UserDetails user = (UserDetails) auth.getPrincipal();
		
		var userBankDetails = bankRepo.getInfo(user.getUsername());
		
		NumberFormat nF= NumberFormat.getInstance();
		nF.setMinimumFractionDigits(2);
		
		String amountReadable = nF.format(userBankDetails.getAmount());
		
		model.addAttribute("currentBalance", amountReadable);
		
		return "transactions.html";
	}
	
	@PostMapping("/transactions")
	public String transactionsPost(@RequestParam(required = false) String transferTarget, @RequestParam String transactionType, @RequestParam String transactionAmount,
									Authentication auth, Model model) {
		
		UserDetails user = (UserDetails) auth.getPrincipal();
		Transactions transaction = new Transactions();
		transaction.setUsername(user.getUsername());
		
		var userBankDetails = bankRepo.getInfo(user.getUsername());
		NumberFormat nF= NumberFormat.getInstance();
		nF.setMinimumFractionDigits(2);
		
		String amountReadable = nF.format(userBankDetails.getAmount());
		
		model.addAttribute("currentBalance", amountReadable);
		
		if (transferTarget != null) {
			transaction = createTransferTransaction(transferTarget, transactionAmount, transactionType);
		} else {
			transaction = createTransactionWithdrawAndDeposit(transactionAmount, transactionType);
		}
		
		System.out.println(transactionCheckService.validTransaction(transaction, userBankDetails));
	
		
		
		if (transactionCheckService.validTransaction(transaction, userBankDetails) == false) {
			String invalidTransaction = "The transaction is invalid.";
			model.addAttribute("transactionError", invalidTransaction);
			return "transactions.html";
		} else {
			transactionService.addTransaction(transaction, user);
			return "redirect:/profile";
		} 
	}
	
	
	
	private Transactions createTransactionWithdrawAndDeposit(String transactionAmount, String transactionType) {
		
		Transactions transaction = new Transactions();
		
		BigDecimal amount = new BigDecimal(Double.parseDouble(transactionAmount));
		
		transaction.setTransactionAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setTransactionDate(LocalDate.now());
		
		return transaction;
	}
	
	private Transactions createTransferTransaction(String targetAccount, String transactionAmount, String transactionType) {
		Transactions transferTransaction = new Transactions();
		
		
		BigDecimal amount = new BigDecimal(Double.parseDouble(transactionAmount));
		
		transferTransaction.setTransactionAmount(amount);
		transferTransaction.setTransactionType(transactionType);
		transferTransaction.setTransactionDate(LocalDate.now());
		transferTransaction.setTransactionTarget(targetAccount);	
		
		return transferTransaction;
	}
}
