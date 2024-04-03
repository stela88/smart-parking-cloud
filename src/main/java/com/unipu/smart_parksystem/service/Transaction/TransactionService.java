package com.unipu.smart_parksystem.service.Transaction;

import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.entity.Transaction;
import com.unipu.smart_parksystem.error.Transaction.TransactionNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TransactionService {


    @Transactional
    TransactionDto saveTransaction(TransactionDto transactionDto);
    public List<TransactionDto> fetchTransactionList();
    public TransactionDto fetchTransactionById(Long transactionId) throws TransactionNotFoundException;
    public void deleteTransactiontById(Long transactionId);
    @Transactional
    public TransactionDto updateTransaction(Long transactionId, Transaction transaction) throws TransactionNotFoundException;



}
