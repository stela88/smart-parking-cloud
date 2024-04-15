package com.unipu.smart_parksystem.mapper;

import com.unipu.smart_parksystem.dto.ReceiptDto;
import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Transaction;

public class TransactionMapper {
    public static TransactionDto convertEntityToDto(Transaction transaction){
        TransactionDto transactionDto = new TransactionDto();
        ReceiptDto receiptDto = new ReceiptDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        receiptDto.setPrice(transaction.getAmount());
        transactionDto.setAmount(receiptDto.getPrice());
        transactionDto.setCreatedTs(transaction.getCreatedTs());
        transactionDto.setModifiedTs(transaction.getModifiedTs());
        transactionDto.setTicket(TicketMapper.convertEntityToDto(transaction.getTicket()));
        return transactionDto;

    }
}
