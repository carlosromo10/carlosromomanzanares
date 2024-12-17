package com.romo.manzanares.provider.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDto {
    private int id;
    private String name;
    private String rfc;
    private String email;
    private String phone;
    private LocalDate created;
}
