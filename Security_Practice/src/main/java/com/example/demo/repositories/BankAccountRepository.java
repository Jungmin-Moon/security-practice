package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Bank_Accounts;

@Repository
public interface BankAccountRepository extends JpaRepository<Bank_Accounts, Long>{
	
}
