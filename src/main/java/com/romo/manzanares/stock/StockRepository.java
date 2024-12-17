package com.romo.manzanares.stock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface StockRepository extends ListCrudRepository<Stock, Integer> {
}
