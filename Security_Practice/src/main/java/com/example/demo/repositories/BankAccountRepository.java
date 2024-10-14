package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.BankAccounts;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccounts, Long>{
	
	@Query("SELECT u from BankAccounts u where u.username = :username")
	public BankAccounts getInfo(@Param("username")String username);
}
