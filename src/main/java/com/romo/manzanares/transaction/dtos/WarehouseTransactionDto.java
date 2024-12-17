package com.romo.manzanares.transaction.dtos;

import com.romo.manzanares.employee.dtos.EmployeeDto;
import com.romo.manzanares.product.dtos.ProductDto;
import com.romo.manzanares.transaction.TransactionType;
import com.romo.manzanares.warehouse.dtos.WarehouseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransactionDto {
    private int id;
    private WarehouseDto warehouseDto;
    private ProductDto productDto;
    private Timestamp date;
    private int quantity;
    private TransactionType transactionType;
    private int flow_id;
    private WarehouseDto destinyWarehouseDto;
    private EmployeeDto employeeDto;
}
