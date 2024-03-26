package com.unipu.smart_parksystem.repository;

import com.unipu.smart_parksystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket save (Ticket ticket);

    List<Ticket> findRegistration(@Param("registration")String registration);

    List<Ticket> findByRegistrationIgnoreCase(String registration);
}
