package com.unipu.smart_parksystem.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDto {
    private Long transactionId;
    private BigDecimal amount;
    private Instant createdTs;
    private Instant modifiedTs;
    private TicketDto ticket;

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
