package com.romo.manzanares.transaction.dtos;

import com.romo.manzanares.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class CreateTransactionDto {
    private int warehouse_id;
    private long product_id;
    private Timestamp date;
    private int quantity;
    private TransactionType transactionType;
    private int flow_id;
    private int employee_id;
}
