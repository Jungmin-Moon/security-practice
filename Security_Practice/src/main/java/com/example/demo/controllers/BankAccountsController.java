package com.example.demo.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.BankAccounts;
import com.example.demo.repositories.BankAccountRepository;

@RestController
@RequestMapping("/bankaccounts")
public class BankAccountsController {
	
	BankAccountRepository bankRepo;
	
	BankAccountsController(BankAccountRepository bankRepo) {
		this.bankRepo = bankRepo;
	}
	
	
	@GetMapping("/all")
	public List<BankAccounts> bankAccountList() {
		return bankRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable long id) {
		Optional<BankAccounts> bankAcc = bankRepo.findById(id);
		if (bankAcc.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND)
					.header("Bank Account", "User")
					.body(bankRepo.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No such user exists.");
		}
	}
	
	@GetMapping("/amounts")
	public List<BigDecimal> getAllAmounts() {
		return bankRepo.getAmounts();
	}
	
}
