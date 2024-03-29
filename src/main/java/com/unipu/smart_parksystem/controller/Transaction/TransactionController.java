package com.unipu.smart_parksystem.controller.Transaction;

import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.entity.Transaction;
import com.unipu.smart_parksystem.service.Ticket.TicketingService;
import com.unipu.smart_parksystem.service.Transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private TicketingService ticketingService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/transactions")
    public Transaction saveTransaction(@RequestBody Transaction transaction){
        LOGGER.info("Inside saveTransaction od TransactionController");

        return transactionService.saveTransaction(transaction);
    }

    @GetMapping("/transactions")
    public List<TransactionDto> fetchTransactionList(){
        LOGGER.info("Inside fetchTransactionList of TransactionController");
        return transactionService.fetchTransactionList();
    }



}
