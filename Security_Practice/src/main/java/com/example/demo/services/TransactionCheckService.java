package com.example.demo.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.demo.entities.BankAccounts;
import com.example.demo.entities.Transactions;

@Component
public class TransactionCheckService {
	
	private final BigDecimal minWithdrawAmount = new BigDecimal(20);
	private final BigDecimal minInBank = new BigDecimal(100);
	private final BigDecimal minDepositAmount = new BigDecimal(20);
	
	public boolean validTransaction(Transactions transaction, BankAccounts userBankDetails) {
		boolean canPerformTransaction = false;
		
		if (transaction.getTransactionType().equals("withdraw")) {
			canPerformTransaction = ifWithdraw(transaction, userBankDetails);
		}
		
		if (transaction.getTransactionType().equals("transfer")) {
			canPerformTransaction = ifTransfer(transaction, userBankDetails);
		}
		
		if (transaction.getTransactionType().equals("deposit")) {
			canPerformTransaction = ifDeposit(transaction, userBankDetails);
		}
		return canPerformTransaction;
	}
	
	
	
	public boolean ifWithdraw(Transactions transaction, BankAccounts userBankDetails) {
		boolean valid = false;
		
		if (transaction.getTransactionAmount().compareTo(minWithdrawAmount) >= 0) {
			
			BigDecimal tempUserAmount = userBankDetails.getAmount();
			BigDecimal potentialLeftInAccount = tempUserAmount.subtract(transaction.getTransactionAmount());
			
			if (potentialLeftInAccount.compareTo(minInBank) < 0) {
				valid = false;
			} else {
				valid = true;
			}
			
		} else {
			return valid;
		}
		
		
		return valid;
	}
	
	
	public boolean ifTransfer(Transactions transaction, BankAccounts userBankDetails) {
		boolean valid = false;
		
		BigDecimal tempUserAmount = userBankDetails.getAmount();
		BigDecimal potentialLeftInAccount = tempUserAmount.subtract(transaction.getTransactionAmount());
		
		if (potentialLeftInAccount.compareTo(minInBank) < 0) {
			valid = false;
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	public boolean ifDeposit(Transactions transaction, BankAccounts userBankDetails) {
		boolean valid = false;
		
		int comparison = transaction.getTransactionAmount().compareTo(minDepositAmount);
		
		if (comparison == -1) {
			valid = false;
		} else {
			valid =  true;
		}
		
		return valid;
	}
}
