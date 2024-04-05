package com.unipu.smart_parksystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReceiptDto {
    private BigDecimal price;
    private Instant timeFrom;
    private Instant timeUntil;
}
