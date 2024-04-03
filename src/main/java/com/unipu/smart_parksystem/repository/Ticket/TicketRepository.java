package com.unipu.smart_parksystem.repository.Ticket;

import com.unipu.smart_parksystem.dto.TicketDto;
import com.unipu.smart_parksystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByRegistrationIgnoreCase(String registration);

    List<Ticket> findByTimeOfExitIsNull();

    List<Ticket> findByRegistrationAndTimeOfExitIsNullAndTimeOfEnterIsNotNull(String registration);
}
