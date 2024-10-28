package com.example.demo.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.BankAccounts;

import jakarta.transaction.Transactional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccounts, Long>{
	
	@Query("SELECT u from BankAccounts u where u.username = :username")
	public BankAccounts getInfo(@Param("username")String username);
	
	@Modifying
	@Transactional
	@Query("Update BankAccounts u set u.amount = ?2 where u.username = ?1")
	public void updateAccountAmount(@Param("username")String username, BigDecimal newAmount);
}
