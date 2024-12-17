package com.romo.manzanares.warehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto {
    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
