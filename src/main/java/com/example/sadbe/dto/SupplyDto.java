package com.example.sadbe.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SupplyDto {

    private Long amount;
    private LocalDateTime date;
    private UUID supplied_parts;

}
