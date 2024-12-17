package com.romo.manzanares;

import com.romo.manzanares.stock.Stock;
import com.romo.manzanares.stock.dtos.StockDto;
import com.romo.manzanares.transaction.Transaction;
import com.romo.manzanares.transaction.dtos.ClientTransactionDto;
import com.romo.manzanares.transaction.dtos.CommonTransactionDto;
import com.romo.manzanares.transaction.dtos.ProviderTransactionDto;
import com.romo.manzanares.transaction.dtos.WarehouseTransactionDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManzanaresApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManzanaresApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Stock.class, StockDto.class)
                .addMappings(mapper -> {
                    mapper.map(Stock::getId, StockDto::setId);
                    mapper.map(Stock::getQuantity, StockDto::setQuantity);
                    mapper.map(Stock::getWarehouse, StockDto::setWarehouseDto);
                    mapper.map(Stock::getProduct, StockDto::setProductDto);
                    mapper.map(Stock::getLocation, StockDto::setLocationDto);
                });

        modelMapper.typeMap(Transaction.class, CommonTransactionDto.class)
                .addMappings(mapper -> {
                    mapper.map(Transaction::getId, CommonTransactionDto::setId);
                    mapper.map(Transaction::getWarehouse, CommonTransactionDto::setWarehouseDto);
                    mapper.map(Transaction::getProduct, CommonTransactionDto::setProductDto);
                    mapper.map(Transaction::getDate, CommonTransactionDto::setDate);
                    mapper.map(Transaction::getQuantity, CommonTransactionDto::setQuantity);
                    mapper.map(Transaction::getTransactionType, CommonTransactionDto::setTransactionType);
                    mapper.map(Transaction::getFlow_id, CommonTransactionDto::setFlow_id);
                    mapper.map(Transaction::getEmployee, CommonTransactionDto::setEmployeeDto);
                });

        modelMapper.typeMap(Transaction.class, WarehouseTransactionDto.class)
                .addMappings(mapper -> {
                    mapper.map(Transaction::getId, WarehouseTransactionDto::setId);
                    mapper.map(Transaction::getWarehouse, WarehouseTransactionDto::setWarehouseDto);
                    mapper.map(Transaction::getProduct, WarehouseTransactionDto::setProductDto);
                    mapper.map(Transaction::getDate, WarehouseTransactionDto::setDate);
                    mapper.map(Transaction::getQuantity, WarehouseTransactionDto::setQuantity);
                    mapper.map(Transaction::getTransactionType, WarehouseTransactionDto::setTransactionType);
                    mapper.map(Transaction::getEmployee, WarehouseTransactionDto::setEmployeeDto);
                });

        modelMapper.typeMap(Transaction.class, ProviderTransactionDto.class)
                .addMappings(mapper -> {
                    mapper.map(Transaction::getId, ProviderTransactionDto::setId);
                    mapper.map(Transaction::getWarehouse, ProviderTransactionDto::setWarehouseDto);
                    mapper.map(Transaction::getProduct, ProviderTransactionDto::setProductDto);
                    mapper.map(Transaction::getDate, ProviderTransactionDto::setDate);
                    mapper.map(Transaction::getQuantity, ProviderTransactionDto::setQuantity);
                    mapper.map(Transaction::getTransactionType, ProviderTransactionDto::setTransactionType);
                    mapper.map(Transaction::getEmployee, ProviderTransactionDto::setEmployeeDto);
                });

        modelMapper.typeMap(Transaction.class, ClientTransactionDto.class)
                .addMappings(mapper -> {
                    mapper.map(Transaction::getId, ClientTransactionDto::setId);
                    mapper.map(Transaction::getWarehouse, ClientTransactionDto::setWarehouseDto);
                    mapper.map(Transaction::getProduct, ClientTransactionDto::setProductDto);
                    mapper.map(Transaction::getDate, ClientTransactionDto::setDate);
                    mapper.map(Transaction::getQuantity, ClientTransactionDto::setQuantity);
                    mapper.map(Transaction::getTransactionType, ClientTransactionDto::setTransactionType);
                    mapper.map(Transaction::getEmployee, ClientTransactionDto::setEmployeeDto);
                });

        return modelMapper;
    }

}
