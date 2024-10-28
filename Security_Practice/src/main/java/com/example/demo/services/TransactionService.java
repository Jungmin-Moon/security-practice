package com.example.demo.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entities.BankAccounts;
import com.example.demo.entities.Transactions;
import com.example.demo.repositories.BankAccountRepository;
import com.example.demo.repositories.TransactionsRepository;

@Service
public class TransactionService {
	
	@Autowired
	TransactionsRepository transactionsRepo;
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	
	public List<Transactions> getTransactions(String username) {
		
		return transactionsRepo.getTransactions(username);
	}
	
	public void addTransaction(Transactions transaction, UserDetails user) {
		
		if (transaction.getTransactionType().equals("deposit")) {
			transactionDeposit(user, transaction);
		} else if (transaction.getTransactionType().equals("withdraw")) {
			transactionWithdraw(user, transaction);
		} else {
			transactionTransfer(user, transaction);
		}
		
		transactionsRepo.save(transaction);
	}
	
	public void transactionDeposit(UserDetails user, Transactions transaction) {
		transaction.setTransactionTarget(user.getUsername());
		transaction.setUsername(user.getUsername());
		
		BankAccounts userAccount = bankAccountRepository.getInfo(user.getUsername());
		
		BigDecimal amountInAccount = userAccount.getAmount();
		BigDecimal difference = amountInAccount.add(transaction.getTransactionAmount());
		
		//userAccount.setAmount(difference);
		bankAccountRepository.updateAccountAmount(user.getUsername(), difference);
	}
	
	public void transactionWithdraw(UserDetails user, Transactions transaction) {
		transaction.setTransactionTarget(user.getUsername());
		transaction.setUsername(user.getUsername());
		
		BankAccounts userAccount = bankAccountRepository.getInfo(user.getUsername());
		
		BigDecimal amountInAccount = userAccount.getAmount();
		BigDecimal difference = amountInAccount.subtract(transaction.getTransactionAmount());
		
		//userAccount.setAmount(difference);
		bankAccountRepository.updateAccountAmount(user.getUsername(), difference);
	}
	
	public void transactionTransfer(UserDetails user, Transactions transaction) {
		transaction.setUsername(user.getUsername());
		System.out.println(transaction.getTransactionTarget());
		
		BankAccounts senderAccount = bankAccountRepository.getInfo(user.getUsername());
		BankAccounts receiverAccount = bankAccountRepository.getInfo(transaction.getTransactionTarget());
		
		BigDecimal senderAmount = senderAccount.getAmount();
		BigDecimal receiverAmount = receiverAccount.getAmount();
		
		BigDecimal senderLoses = senderAmount.subtract(transaction.getTransactionAmount());
		BigDecimal receiverGains = receiverAmount.add(transaction.getTransactionAmount());
		
		bankAccountRepository.updateAccountAmount(user.getUsername(), senderLoses);
		bankAccountRepository.updateAccountAmount(transaction.getTransactionTarget(), receiverGains);
	}
	
}
