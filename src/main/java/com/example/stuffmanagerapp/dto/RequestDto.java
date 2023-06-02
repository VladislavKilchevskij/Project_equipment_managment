package com.example.stuffmanagerapp.dto;

import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequestDto {
    private Integer id;
    private Date regDate;
    private MaintenanceDto maintenance;
    private EmployeeShortDto employee;
    private List<RepairHistoryDto> historyList = new ArrayList<>();
    private Integer rowAmount;
}
