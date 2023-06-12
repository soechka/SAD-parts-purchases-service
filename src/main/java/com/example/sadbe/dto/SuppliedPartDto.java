package com.example.sadbe.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SuppliedPartDto {

    private UUID part;
    private LocalDateTime date;
    private Long cost;
    private UUID supplier;

}
