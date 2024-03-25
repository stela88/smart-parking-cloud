package com.unipu.smart_parksystem.entity;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;

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
    private double amount;
//    private Long ticketId;
    private Timestamp created;
    private Timestamp modified;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "ticket_id",
            referencedColumnName = "ticketId"
    )
    private Ticket ticket;





}
