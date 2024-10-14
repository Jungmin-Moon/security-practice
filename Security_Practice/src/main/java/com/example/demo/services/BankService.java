package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.BankAccounts;
import com.example.demo.entities.User;
import com.example.demo.repositories.BankAccountRepository;

@Service
public class BankService {

	@Autowired
	BankAccountRepository bankAccountRepo;
	
	public void createBankAccount(User user) {
		BankAccounts bankAcc = new BankAccounts();
		
		bankAcc.setAccountId(user.getId());
		bankAcc.setAmount(0);
		bankAcc.setUsername(user.getUsername());
		
		bankAccountRepo.save(bankAcc);
	}
}
