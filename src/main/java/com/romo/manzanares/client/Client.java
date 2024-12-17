package com.romo.manzanares.client;

import com.romo.manzanares.stock.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

// Lombok
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String rfc;
    private String email;
    private String phone;
    private LocalDate created;
    private boolean deleted = Boolean.FALSE;
}
