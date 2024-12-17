package com.romo.manzanares.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProductDto {
    private long upc;
    private String description;
    private double lastCost;
    private double lastPrice;
}
