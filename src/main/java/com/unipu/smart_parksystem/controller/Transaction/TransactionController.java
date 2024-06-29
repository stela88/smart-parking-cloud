package com.unipu.smart_parksystem.controller.Transaction;

import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.error.Transaction.TransactionNotFoundException;
import com.unipu.smart_parksystem.service.Ticket.TicketingService;
import com.unipu.smart_parksystem.service.Transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private TicketingService ticketingService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(TransactionController.class);

    @PostMapping()
    public TransactionDto saveTransaction(@RequestBody TransactionDto transaction){
        LOGGER.info("Inside saveTransaction od TransactionController");

        return transactionService.saveTransaction(transaction);
    }

    @GetMapping()
    public List<TransactionDto> fetchTransactionList(){
        LOGGER.info("Inside fetchTransactionList of TransactionController");
        return transactionService.fetchTransactionList();
    }

    @GetMapping("/{id}")
    public TransactionDto fetchTransactionById(@PathVariable("id") Long transactionId)
            throws TransactionNotFoundException{
        return transactionService.fetchTransactionById(transactionId);
    }

    @DeleteMapping("/{id}")
    public String deleteTransactionById(@PathVariable("id") Long transactionId){
        transactionService.deleteTransactiontById(transactionId);
        return "Transaction deleted successfully";
    }

}
