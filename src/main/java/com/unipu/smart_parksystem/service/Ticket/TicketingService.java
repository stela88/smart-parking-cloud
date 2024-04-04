package com.unipu.smart_parksystem.service.Ticket;

import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.error.Ticket.TicketNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface TicketingService {

    TicketDto saveTicket(String registration);
    List<TicketDto> fetchTicketList();
    Ticket fetchTicketByRegistration(String registration);
    TicketDto fetchTicketById(Long ticketId) throws TicketNotFoundException;
    void deleteTicketById(Long ticketId);
    @Transactional
    TicketDto updateTicket(Long ticketId, TicketDto ticket) throws TicketNotFoundException;
    List<Ticket> fetchActiveTickets();
    List<Ticket> fetchActiveTicketsByRegistration(String registration);

}
