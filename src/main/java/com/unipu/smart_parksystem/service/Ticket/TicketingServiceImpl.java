package com.unipu.smart_parksystem.service.Ticket;

import com.unipu.smart_parksystem.constants.Constants;
import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.error.Ticket.TicketNotFoundException;
import com.unipu.smart_parksystem.error.Transaction.TransactionNotFoundException;
import com.unipu.smart_parksystem.mapper.TicketMapper;
import com.unipu.smart_parksystem.repository.Ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketingServiceImpl implements TicketingService {


    private final TicketRepository ticketRepository;

    @Autowired
    public TicketingServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public Ticket saveTicket(String registration) {
        Instant now = Instant.now();
        Instant exitTimeout = now.plus(Constants.MINUTES_FOR_TIMEOUT, ChronoUnit.MINUTES);

        List<Ticket> activeTickets = ticketRepository.findByRegistrationAndTimeOfExitIsNullAndTimeOfEnterIsNotNull(registration);
        if (!activeTickets.isEmpty()) {
            throw new IllegalArgumentException("Already have active registrations");
        }

        List<Ticket> allActiveTickets = ticketRepository.findByTimeOfExitIsNull();
        if (allActiveTickets.size() >= Constants.GARAGE_CAPACITY) {
            throw new IllegalArgumentException("Garage capacity is full");
        }

        Ticket activeTicket = Ticket.builder()
                .timeOfEnter(now)
                .exitTimeout(exitTimeout)
                .registration(registration)
                .createdTs(now)
                .build();

        return ticketRepository.save(activeTicket);
    }

    @Override
    public List<TicketDto> fetchTicketList() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            TicketDto ticketDto = TicketMapper.convertEntityToDto(ticket);
            ticketDtoList.add(ticketDto);
        }
        return ticketDtoList;
    }


    @Override
    public Ticket fetchTicketByRegistration(String registration) {
        return ticketRepository.findByRegistrationIgnoreCase(registration);
    }


    @Override
    public TicketDto fetchTicketById(Long ticketId) throws TicketNotFoundException {
        Optional<Ticket> ticket =
                ticketRepository.findById(ticketId);

        if (!ticket.isPresent()) {
            throw new TicketNotFoundException("Ticket Not Available");
        }

        return TicketMapper.convertEntityToDto(ticket.get());
    }

    @Override
    public void deleteTicketById(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }


    @Override
    @Transactional
    public TicketDto updateTicket(Long ticketId, TicketDto ticket) throws TicketNotFoundException {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isEmpty()) {
            throw new TicketNotFoundException("Ticket not found");
        }

        Ticket ticketDB = ticketOptional.get();

        if (Objects.nonNull(ticket.getRegistration())) {
            ticketDB.setRegistration(ticket.getRegistration());
        }
        if (Objects.nonNull(ticket.getTimeOfEnter())) {
            ticketDB.setTimeOfEnter(ticket.getTimeOfEnter());
        }
        if (Objects.nonNull(ticket.getTimeOfExit())) {
            ticketDB.setTimeOfExit(ticket.getTimeOfExit());
        }
        if (Objects.nonNull(ticket.getPrice())) {
            ticketDB.setPrice(ticket.getPrice());
        }
        if (Objects.nonNull(ticket.getExitTimeout())) {
            ticketDB.setExitTimeout(ticket.getExitTimeout());
        }
        if (Objects.nonNull(ticket.getCreatedTs())) {
            ticketDB.setCreatedTs(ticket.getCreatedTs());
        }
        if (Objects.nonNull(ticket.getModifiedTs())) {
            ticketDB.setModifiedTs(ticket.getModifiedTs());
        }

        return TicketMapper.convertEntityToDto(ticketRepository.save(ticketDB));
    }


    @Override
    public List<Ticket> fetchActiveTickets() {
        return ticketRepository.findByTimeOfExitIsNull();
    }

    public List<Ticket> fetchActiveTicketsByRegistration(String registration) {
        return ticketRepository.findByRegistrationAndTimeOfExitIsNullAndTimeOfEnterIsNotNull(registration);
    }

    @Override
    public BigDecimal fetchTicketPriceByRegistration(String registration) {
        Ticket ticket = ticketRepository.findByRegistrationIgnoreCase(registration);
        if (ticket != null) {
            return ticket.getPrice();
        } else {
            throw new IllegalArgumentException("Ticket not found");
        }
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
