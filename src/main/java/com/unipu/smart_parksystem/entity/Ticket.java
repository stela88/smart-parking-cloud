package com.unipu.smart_parksystem.entity;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;

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
    private String registration;
    private Timestamp timeOfEnter;
    private Timestamp timeOfExit;
    private Double price;
    private Timestamp exitTimeout;
    private boolean created;
    private String modified;


}
