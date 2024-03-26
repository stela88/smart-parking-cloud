package com.unipu.smart_parksystem.service;

import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.error.TicketNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TicketingService {

    public Ticket saveTicket(Ticket ticket);
    List<Ticket> fetchTicketByRegistration(String registration);

    public Ticket fetchTicketById(Long ticketId) throws TicketNotFoundException;
    public void deleteTicketById(Long ticketId);
    public Ticket updateTicket(Long ticketId, Ticket ticket);
    Optional<Ticket> findTicketById(Long ticketId);

}
