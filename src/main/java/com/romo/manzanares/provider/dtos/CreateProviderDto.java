package com.romo.manzanares.provider.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateProviderDto {
    private String name;
    private String rfc;
    private String email;
    private String phone;
    private LocalDate created;
}
