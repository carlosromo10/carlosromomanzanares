package com.romo.manzanares.transaction;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.romo.manzanares.employee.Employee;
import com.romo.manzanares.location.Location;
import com.romo.manzanares.product.Product;
import com.romo.manzanares.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@AllArgsConstructor
@SQLDelete(sql = "UPDATE stock SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name="warehouse_id", nullable=false)
    private Warehouse warehouse;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    private Timestamp date;

    private int quantity;

    private TransactionType transactionType;

    private int flow_id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    private boolean deleted = Boolean.FALSE;
}
