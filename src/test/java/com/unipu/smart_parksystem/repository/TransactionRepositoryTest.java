package com.unipu.smart_parksystem.repository;

import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.entity.Transaction;
import com.unipu.smart_parksystem.repository.Transaction.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class TransactionRepositoryTest {

    Instant timeOfCreating = Instant.now();
    Instant timeOfExit = timeOfCreating.plus(60, ChronoUnit.MINUTES);
    long minutesDifference = ChronoUnit.MINUTES.between(timeOfCreating, timeOfExit);
    BigDecimal totalPrice = (new BigDecimal(minutesDifference)).multiply(new BigDecimal("0.02"));

    Instant timeOfExitTimeout = timeOfExit.plus(15, ChronoUnit.MINUTES);

    @Autowired
    private TransactionRepository transactionRepository;


    @Test
    public void saveTranasaction() {
        Transaction transaction =
                Transaction.builder()
                        .amount(totalPrice)
                        .createdTs(timeOfCreating)
                        .build();

        transactionRepository.save(transaction);
    }

    @Test
    public void saveTransactionWithTicketId(){
        Ticket ticket = Ticket.builder()
                .registration("zg556pl")
                .timeOfEnter(timeOfCreating)
                .timeOfExit(timeOfExit)
                .createdTs(timeOfCreating)
                .price(totalPrice)
                .build();

        Transaction transaction = Transaction.builder()
                .amount(totalPrice)
                .modifiedTs(timeOfCreating)
                .ticket(ticket)
                .build();

        transactionRepository.save(transaction);
    }


}