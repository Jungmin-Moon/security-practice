package com.example.demo.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Transactions;
import com.example.demo.repositories.TransactionsRepository;

@Service
public class TransactionService {
	
	@Autowired
	TransactionsRepository transactionsRepo;
	
	public List<Transactions> getTransactions(String username) {
		
		return transactionsRepo.getTransactions(username);
	}
	
	
	
}
