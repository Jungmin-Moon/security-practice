package com.example.demo.services;

import com.example.demo.entities.Transactions;

public class TransactionCheckService {
	private final int minInBank = 100;
	private final int minWithDrawAmount = 20;
	private final int minDepositAmount = 20;
	
	
	
	public boolean validTransaction(Transactions transaction) {
		boolean canPerformTransaction = false;
		
		
		
		
		
		
		
		return canPerformTransaction;
	}
	
	
	
	public boolean ifWithdraw(Transactions transaction) {
		boolean valid = false;
		
		
		
		
		
		return valid;
	}
	
	
	public boolean ifTransfer(Transactions transaction) {
		boolean valid = false;
		
		
		
		return valid;
	}
	
	public boolean ifDeposit(Transactions transaction) {
		boolean valid = false;
		
		
		
		return valid;
	}
}
