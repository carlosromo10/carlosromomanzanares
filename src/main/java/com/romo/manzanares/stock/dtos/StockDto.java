package com.romo.manzanares.stock.dtos;

import com.romo.manzanares.location.dtos.LocationDto;
import com.romo.manzanares.product.dtos.ProductDto;
import com.romo.manzanares.warehouse.Warehouse;
import com.romo.manzanares.warehouse.dtos.WarehouseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    private int id;

    private WarehouseDto warehouseDto;

    private ProductDto productDto;

    private LocationDto locationDto;

    private int quantity;
}
