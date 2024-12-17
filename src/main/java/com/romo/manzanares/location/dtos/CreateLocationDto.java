package com.romo.manzanares.location.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateLocationDto {
    private String aisle;
    private String shelf;
    private String level;
}
