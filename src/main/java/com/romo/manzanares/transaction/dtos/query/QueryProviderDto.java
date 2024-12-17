package com.romo.manzanares.transaction.dtos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class QueryProviderDto {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
}
