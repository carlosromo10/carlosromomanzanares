package com.romo.manzanares.location.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private int id;
    private String aisle;
    private String shelf;
    private String level;
}
