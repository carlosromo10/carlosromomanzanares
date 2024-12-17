    package com.romo.manzanares.provider;

import com.romo.manzanares.stock.Stock;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@AllArgsConstructor
@SQLDelete(sql = "UPDATE provider SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Provider {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String rfc;
    private String email;
    private String phone;
    private LocalDate created;
    private boolean deleted = Boolean.FALSE;

}
