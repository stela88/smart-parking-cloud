package com.unipu.smart_parksystem.repository;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionRepositoryTest {

    LocalDateTime timeOfCreating = LocalDateTime.now();
    LocalDateTime timeOfExit = timeOfCreating.plus(60, ChronoUnit.MINUTES);

    double pricePerMinute = 0.02;
    long minutesDifference = ChronoUnit.MINUTES.between(timeOfCreating, timeOfExit);
    double totalPrice = minutesDifference * pricePerMinute;

    LocalDateTime timeOfExitTimeout = timeOfExit.plusMinutes(15);

    @Autowired
    private TransactionRepository transactionRepository;


    @Test
    public void saveTranasaction() {
        Transaction transaction =
                Transaction.builder()
                        .amount(totalPrice)
                        .created(Timestamp.valueOf(timeOfCreating))
                        .build();

        transactionRepository.save(transaction);
    }

    @Test
    public void saveTransactionWithTicketId(){
        Ticket ticket = Ticket.builder()
                .registration("zg556pl")
                .timeOfEnter(Timestamp.valueOf(timeOfCreating))
                .timeOfExit(Timestamp.valueOf(timeOfExit))
                .created(true)
                .price(totalPrice)
                .build();

        Transaction transaction = Transaction.builder()
                .created(Timestamp.valueOf(timeOfCreating))
                .amount(totalPrice)
                .modified(Timestamp.valueOf(timeOfCreating))
                .ticket(ticket)
                .build();

        transactionRepository.save(transaction);
    }


}