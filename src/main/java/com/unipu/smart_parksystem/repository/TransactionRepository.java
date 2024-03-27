package com.unipu.smart_parksystem.repository;

import com.unipu.smart_parksystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
