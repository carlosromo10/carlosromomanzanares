package com.romo.manzanares.warehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateWarehouseDto {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
