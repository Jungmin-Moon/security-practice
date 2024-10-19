package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>{
	
	@Query("Select t from Transactions t where t.username = :username")
	public List<Transactions> getTransactions(@Param("username")String username);
	
}
