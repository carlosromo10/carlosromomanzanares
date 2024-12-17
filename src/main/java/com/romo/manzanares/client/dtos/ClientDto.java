package com.romo.manzanares.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto {
    private int id;
    private String name;
    private String rfc;
    private String email;
    private String phone;
    private LocalDate created;
}
