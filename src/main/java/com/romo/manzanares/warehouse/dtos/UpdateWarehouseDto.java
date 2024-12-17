package com.romo.manzanares.warehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UpdateWarehouseDto {
    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
