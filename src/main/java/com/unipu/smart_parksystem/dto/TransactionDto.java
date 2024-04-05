package com.unipu.smart_parksystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDto {
    private Long transactionId;
    private BigDecimal amount;
    private Instant createdTs;
    private Instant modifiedTs;
    private TicketDto ticket;
}
