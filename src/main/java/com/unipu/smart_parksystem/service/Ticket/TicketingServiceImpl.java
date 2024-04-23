package com.unipu.smart_parksystem.service.Ticket;

import com.unipu.smart_parksystem.constants.Constants;
import com.unipu.smart_parksystem.dto.ExitDto;
import com.unipu.smart_parksystem.dto.ReceiptDto;
import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.error.Ticket.TicketNotFoundException;
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

import static com.unipu.smart_parksystem.util.TicketingUtil.hoursToPay;

@Service
public class TicketingServiceImpl implements TicketingService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketingServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public TicketDto saveTicket(String registration) {
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

        if(registration.isEmpty()){
            throw  new IllegalArgumentException("Registration can not be null");
        }

        Ticket activeTicket = Ticket.builder()
                .timeOfEnter(now)
                .exitTimeout(exitTimeout)
                .registration(registration)
                .createdTs(now)
                .build();

        return TicketMapper.convertEntityToDto(ticketRepository.save(activeTicket));
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
    @Transactional
    public ExitDto canExit(String registration) {
        List<Ticket> tickets = fetchActiveTicketsByRegistration(registration);
        Instant now = Instant.now();
        checkTickets(tickets);
        Ticket ticket = tickets.get(0);

        if (ticket.getExitTimeout().isAfter(now)) {
            return ExitDto.builder()
                    .canExit(true)
                    .build();
        }

        return ExitDto.builder()
                .canExit(false)
                .build();
    }

    private void checkTickets(List<Ticket> tickets) {
        if (tickets.size() > 1) {
            throw new IllegalArgumentException("Can't exit if you have more than 1 ticket");
        }
        if (tickets.isEmpty()) {
            throw new IllegalArgumentException("Can't exit if you have no tickets");
        }
    }

    @Override
    @Transactional
    public ExitDto exit(String registration) {
        ExitDto exitDto = canExit(registration);
        List<Ticket> tickets = fetchActiveTicketsByRegistration(registration);
        checkTickets(tickets);

        Ticket ticket = tickets.get(0);

        if (exitDto == null) {
            throw new RuntimeException("Something went wrong");
        }

        if (exitDto.getCanExit()) {
            ticket.setTimeOfExit(Instant.now());
            ticket = ticketRepository.save(ticket);
            return ExitDto.builder()
                    .canExit(true)
                    .timeOfExit(ticket.getTimeOfExit())
                    .build();
        }

        return ExitDto.builder()
                .canExit(false)
                .build();
    }

    @Override
    public Ticket fetchTicketByRegistration(String registration) {
        Ticket ticket = ticketRepository.findByRegistrationIgnoreCase(registration);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket not found for registration: " + registration);
        }
        return ticket;
    }

    @Override
    public TicketDto fetchTicketById(Long ticketId) {
        Optional<Ticket> ticket =
                ticketRepository.findById(ticketId);

        if (!ticket.isPresent()) {
            throw new TicketNotFoundException("Ticket Not Available");
        }

        return TicketMapper.convertEntityToDto(ticket.get());
    }

    @Override
    public ReceiptDto fetchReceiptByTicketId(Long ticketId) {
        Instant now = Instant.now();
        TicketDto ticketDto = fetchTicketById(ticketId);
        //------------------------------------------ plaćanje
        long hoursToPay = hoursToPay(ticketDto.getExitTimeout().minus(Constants.MINUTES_FOR_TIMEOUT, ChronoUnit.MINUTES), now);
        BigDecimal amountToPay = getAmountToPay(hoursToPay);
        //------------------------------------------
        //------------------------------------------ vrijeme do kad mozes ostati u garazi pod uvjetom da platis sad, tj vrijeme do kad moras platiti
        Instant timeUntil = ticketDto.getExitTimeout().minus(Constants.MINUTES_FOR_TIMEOUT, ChronoUnit.MINUTES).plus(hoursToPay, ChronoUnit.HOURS);
        //------------------------------------------

        return ReceiptDto.builder()
                .price(amountToPay)
                .timeUntil(timeUntil)
                .timeFrom(ticketDto.getExitTimeout().minus(Constants.MINUTES_FOR_TIMEOUT, ChronoUnit.MINUTES))
                .build();
    }

    private BigDecimal getAmountToPay(long hoursToPay) {

        if (hoursToPay < 0) {
            throw new IllegalArgumentException("Can't pay before timeout time outs");
        }

        return BigDecimal.valueOf(Constants.PRICE_PER_HOUR * hoursToPay);
    }

    @Override
    public void deleteTicketById(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    @Transactional
    public TicketDto updateTicket(Long ticketId, TicketDto ticket) {
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

    @Transactional
    public Ticket create(String registration) {
        Instant now = Instant.now();
        Ticket ticket = Ticket.builder()
                .registration(registration)
                .build();
        return ticketRepository.save(ticket);
    }
}
