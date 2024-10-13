package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Bank_Accounts;

@Repository
public interface BankAccountRepository extends JpaRepository<Bank_Accounts, Long>{
	
	
	@Modifying
	@Query("INSERT into bank_accounts (id, username) values (DEFAULT, :username)")
	public void createBankAccount(@Param("username")String username);
}
