package com.romo.manzanares.employee.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateEmployeeDto {
    private int id;
    private String name;
    private String rfc;
    private String email;
    private String phone;
    private LocalDate created;
}
