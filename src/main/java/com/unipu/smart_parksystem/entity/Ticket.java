package com.unipu.smart_parksystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )
    private Long ticketId;
    // todo -> must be not null in db
    private String registration;
    // todo -> must be not null in db
    private Instant timeOfEnter;
    private Instant timeOfExit;
    private BigDecimal price;
    // todo -> must be not null in db
    private Instant exitTimeout;
    private Instant createdTs;
    private Instant modifiedTs;


}
