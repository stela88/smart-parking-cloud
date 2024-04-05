package com.unipu.smart_parksystem.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketDto {
    private Long ticketId;
    // todo -> must be not null in db
    private String registration;
    // todo -> must be not null in db
    private Instant timeOfEnter;
    private Instant timeOfExit;
    // todo -> must be not null in db
    private Instant exitTimeout;
    private Instant createdTs;
    private Instant modifiedTs;

}

