package com.unipu.smart_parksystem.repository;

import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.repository.Ticket.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class TicketRepositoryTest {

    Instant timeOfEnter = Instant.now();
    Instant timeOfExit = timeOfEnter.plus(60, ChronoUnit.MINUTES);
    long minutesDifference = ChronoUnit.MINUTES.between(timeOfEnter, timeOfExit);
    BigDecimal totalPrice = (new BigDecimal(minutesDifference)).multiply(new BigDecimal("0.02"));

    Instant timeOfExitTimeout = timeOfExit.plus(15, ChronoUnit.MINUTES);


    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void saveTicket(){
        Ticket ticket = Ticket
                .builder()
                .registration("zg184pj")
                .timeOfEnter(timeOfEnter)
                .timeOfExit(timeOfExit)
                .price(totalPrice)
                .createdTs(timeOfEnter)
                .exitTimeout(timeOfExitTimeout)
                .build();

        ticketRepository.save(ticket);
    }
}

