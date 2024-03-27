package com.unipu.smart_parksystem.repository;

import com.unipu.smart_parksystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByRegistrationIgnoreCase(String registration);

    List<Ticket> findByTimeOfExitIsNull();

    List<Ticket> findByRegistrationAndTimeOfExitIsNullAndTimeOfEnterIsNotNull(String registration);
}
