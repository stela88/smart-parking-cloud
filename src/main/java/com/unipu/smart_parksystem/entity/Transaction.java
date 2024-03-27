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

public class Transaction {

    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long transactionId;
    private BigDecimal amount;
    private Instant createdTs;
    private Instant modifiedTs;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "ticket_id",
            referencedColumnName = "ticketId"
    )
    private Ticket ticket;





}
