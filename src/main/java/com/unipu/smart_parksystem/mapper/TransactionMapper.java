package com.unipu.smart_parksystem.mapper;

import com.unipu.smart_parksystem.dto.PriceDto;
import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Transaction;

public class TransactionMapper {
    public static TransactionDto convertEntityToDto(Transaction transaction){
        TransactionDto transactionDto = new TransactionDto();
        PriceDto priceDto = new PriceDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        priceDto.setPrice(transaction.getAmount());
        transactionDto.setCreatedTs(transaction.getCreatedTs());
        transactionDto.setModifiedTs(transaction.getModifiedTs());
        transactionDto.setTicket(TicketMapper.convertEntityToDto(transaction.getTicket()));
        return transactionDto;

    }
}
