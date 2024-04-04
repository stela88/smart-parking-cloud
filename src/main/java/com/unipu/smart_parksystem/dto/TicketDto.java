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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Instant getTimeOfEnter() {
        return timeOfEnter;
    }

    public void setTimeOfEnter(Instant timeOfEnter) {
        this.timeOfEnter = timeOfEnter;
    }

    public Instant getTimeOfExit() {
        return timeOfExit;
    }

    public void setTimeOfExit(Instant timeOfExit) {
        this.timeOfExit = timeOfExit;
    }


    public Instant getExitTimeout() {
        return exitTimeout;
    }

    public void setExitTimeout(Instant exitTimeout) {
        this.exitTimeout = exitTimeout;
    }

    public Instant getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Instant createdTs) {
        this.createdTs = createdTs;
    }

    public Instant getModifiedTs() {
        return modifiedTs;
    }

    public void setModifiedTs(Instant modifiedTs) {
        this.modifiedTs = modifiedTs;
    }

}

