package com.unipu.smart_parksystem.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExitDto {
    private Boolean canExit;
    private Instant timeOfExit;
}
