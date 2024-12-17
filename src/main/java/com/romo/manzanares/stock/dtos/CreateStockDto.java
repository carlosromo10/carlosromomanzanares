package com.romo.manzanares.stock.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateStockDto {
    private int warehouse_id;

    private long product_id;

    private int location_id;

    private int quantity;
}
