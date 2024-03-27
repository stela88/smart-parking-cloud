package com.unipu.smart_parksystem.service;

import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.error.TicketNotFoundException;
import com.unipu.smart_parksystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketingServiceImpl implements TicketingService{

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> fetchTicketList(){
        return ticketRepository.findAll();
    }

    @Override
    public Ticket fetchTicketByRegistration(String registration) {
        return ticketRepository.findByRegistrationIgnoreCase(registration);
    }


    @Override
    public Ticket fetchTicketById(Long ticketId) throws TicketNotFoundException{
        Optional<Ticket> ticket =
                ticketRepository.findById(ticketId);

        if(!ticket.isPresent()) {
            throw new TicketNotFoundException("Ticket Not Available");
        }

        return  ticket.get();
    }

    @Override
    public void deleteTicketById(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }


    @Override
    public Ticket updateTicket(Long ticketId, Ticket ticket) {
        Ticket ticketDB = ticketRepository.findById(ticketId).get();

        if(Objects.nonNull(ticket.getRegistration()) &&
                !"".equalsIgnoreCase(ticket.getRegistration())) {
            ticketDB.setRegistration(ticket.getRegistration());
        }

        if(Objects.nonNull(ticket.getTimeOfEnter())){
            ticketDB.setTimeOfEnter(ticket.getTimeOfEnter());
        }

        if(Objects.isNull(ticket.getTimeOfExit())){
            ticketDB.setTimeOfExit(ticket.getTimeOfExit());
        }

        if(Objects.isNull(ticket.getPrice())){
            ticketDB.setPrice(ticket.getPrice());
        }

        if(Objects.nonNull(ticket.getExitTimeout())){
            ticketDB.setExitTimeout(ticket.getExitTimeout());
        }

        if(Objects.isNull(ticket.getCreatedTs())){
            ticketDB.setCreatedTs(ticket.getCreatedTs());
        }

        if(Objects.isNull(ticket.getModifiedTs())){
            ticketDB.setModifiedTs(ticket.getModifiedTs());
        }

        return ticketRepository.save(ticketDB);
    }

    @Override
    public Optional<Ticket> findTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @Override
    public List<Ticket> fetchActiveTickets() {
        return ticketRepository.findByTimeOfExitIsNull();
    }

    public List<Ticket> fetchActiveTicketsByRegistration(String registration) {
        return ticketRepository.findByRegistrationAndTimeOfExitIsNullAndTimeOfEnterIsNotNull(registration);
    }


    @Transactional
    public Ticket create(String registration) {
        Instant now = Instant.now();
        Ticket ticket = Ticket.builder()
                .registration(registration)
                .price(null)
                .build();
        return ticketRepository.save(ticket);
    }
}
