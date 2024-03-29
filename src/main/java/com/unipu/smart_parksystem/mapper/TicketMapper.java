package com.unipu.smart_parksystem.mapper;

import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.entity.Ticket;

public class TicketMapper {
    public static TicketDto convertEntityToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketId(ticket.getTicketId());
        ticketDto.setExitTimeout(ticket.getExitTimeout());
        ticketDto.setRegistration(ticket.getRegistration());
        ticketDto.setTimeOfEnter(ticket.getTimeOfEnter());
        ticketDto.setTimeOfExit(ticket.getTimeOfExit());
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setCreatedTs(ticket.getCreatedTs());
        ticketDto.setModifiedTs(ticket.getModifiedTs());
        return ticketDto;
    }
}
