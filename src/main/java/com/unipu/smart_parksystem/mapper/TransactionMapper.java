package com.unipu.smart_parksystem.mapper;

import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Transaction;

public class TransactionMapper {
    public static TransactionDto convertEntityToDto(Transaction transaction){
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setCreatedTs(transaction.getCreatedTs());
        transactionDto.setModifiedTs(transaction.getModifiedTs());
        transactionDto.setTicket(transaction.getTicket());
        return transactionDto;

    }
}
