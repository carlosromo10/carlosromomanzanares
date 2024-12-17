package com.romo.manzanares.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romo.manzanares.stock.Stock;
import com.romo.manzanares.transaction.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE employee SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String rfc;
    private String email;
    private String phone;
    private LocalDate created;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JsonIgnore
    private List<Transaction> transactions;
}
