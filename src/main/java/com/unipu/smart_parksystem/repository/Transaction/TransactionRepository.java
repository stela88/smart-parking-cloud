package com.unipu.smart_parksystem.repository.Transaction;

import com.unipu.smart_parksystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
