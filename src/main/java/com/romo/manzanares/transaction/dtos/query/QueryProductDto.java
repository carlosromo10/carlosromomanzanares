package com.romo.manzanares.transaction.dtos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class QueryProductDto {
    private long productUpc;
    private LocalDate startDate;
    private LocalDate endDate;
}
