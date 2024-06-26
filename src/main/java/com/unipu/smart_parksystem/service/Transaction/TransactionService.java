package com.unipu.smart_parksystem.service.Transaction;

import com.unipu.smart_parksystem.dto.ReceiptDto;
import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Transaction;
import com.unipu.smart_parksystem.error.Transaction.TransactionNotFoundException;

import java.util.List;

public interface TransactionService {
    TransactionDto saveTransaction(TransactionDto transactionDto);
    List<TransactionDto> fetchTransactionList();
    TransactionDto fetchTransactionById(Long transactionId) throws TransactionNotFoundException;
    void deleteTransactiontById(Long transactionId);

//  TransactionDto updateTransaction(Long transactionId, TransactionDto transaction) throws TransactionNotFoundException;



}
