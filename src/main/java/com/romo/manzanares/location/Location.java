package com.romo.manzanares.location;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romo.manzanares.stock.Stock;
import jakarta.persistence.*;
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
@SQLDelete(sql = "UPDATE location SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String aisle;
    private String shelf;
    private String level;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private List<Stock> stocks;
}
