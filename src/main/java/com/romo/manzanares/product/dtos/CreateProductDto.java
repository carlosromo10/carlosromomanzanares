package com.romo.manzanares.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CreateProductDto {
    private long upc;
    private String description;
    private double lastCost;
    private double lastPrice;
}
