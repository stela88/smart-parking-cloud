package com.unipu.smart_parksystem.controller.Ticket;

import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.error.Ticket.TicketNotFoundException;
import com.unipu.smart_parksystem.service.Ticket.TicketingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController()
public class TicketingController {

    @Autowired
    private TicketingService ticketingService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(TicketingController.class);

    @PostMapping("/tickets")
    public Ticket saveTicket(@RequestBody TicketDto ticket) {
        LOGGER.info("Inside saveTicket of TicketingController");
        if (ticket.getRegistration() == null) {
            throw new IllegalArgumentException("Missing registration");
        }
        return ticketingService.saveTicket(ticket.getRegistration());
    }

    @GetMapping("/tickets")
    public List<TicketDto> fetchTicketList() {
        LOGGER.info("Inside fetchTicketList of TicketingController");
        return ticketingService.fetchTicketList();
    }

    @GetMapping("/tickets/registration/{registration}")
    public Ticket fetchTicketByRegistration(@PathVariable("registration") String registration) {
        return ticketingService.fetchTicketByRegistration(registration);
    }

    @GetMapping("/tickets/{id}")
    public TicketDto fetchTicketById(@PathVariable("id") Long ticketId)
            throws TicketNotFoundException {
        return ticketingService.fetchTicketById(ticketId);
    }

    @DeleteMapping("/tickets/{id}")
    public String deleteTicketById(@PathVariable("id") Long ticketId) {
        ticketingService.deleteTicketById(ticketId);
        return "Ticket deleted successfully";
    }

    @PutMapping("/tickets/{id}")
    public TicketDto updateTicket(@PathVariable("id") Long ticketId,
                                  @RequestBody TicketDto ticket) throws TicketNotFoundException {
        return ticketingService.updateTicket(ticketId, ticket);
    }

    @GetMapping("/tickets/active")
    public List<Ticket> fetchActiveTickets() {
        LOGGER.info("Inside fetchActiveTickets of TicketingController");
        return ticketingService.fetchActiveTickets();
    }

    @GetMapping("/tickets/registration/{registration}/active")
    public List<Ticket> fetchActiveTicketsByRegistration(@PathVariable("registration") String registration) {
        LOGGER.info("Inside fetchActiveTicketsByRegistration of TicketingController");
        return ticketingService.fetchActiveTicketsByRegistration(registration);
    }

    @GetMapping("/tickets/registration/{registration}/price")
    public BigDecimal fetchTicketPriceByRegistration(@PathVariable("registration") String registration){
        return ticketingService.fetchTicketPriceByRegistration(registration);
    }


}
