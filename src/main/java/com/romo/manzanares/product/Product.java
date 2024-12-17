package com.romo.manzanares.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romo.manzanares.stock.Stock;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
@AllArgsConstructor
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE upc=?")
@SQLRestriction("deleted=false")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long upc;
    private String description;
    private double lastCost;
    private double lastPrice;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JsonIgnore
    private List<Stock> stocks;
}
