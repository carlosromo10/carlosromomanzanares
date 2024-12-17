package com.romo.manzanares.product.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long upc;
    private String description;
    private double lastCost;
    private double lastPrice;
}
