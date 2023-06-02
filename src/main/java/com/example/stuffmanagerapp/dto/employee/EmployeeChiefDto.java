package com.example.stuffmanagerapp.dto.employee;

import lombok.Data;

@Data
public class EmployeeChiefDto {
    private Integer id;
    private String surname;
    private String firstName;
    private String patronymic;
    private boolean isChief;
}
