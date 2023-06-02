package com.example.stuffmanagerapp.service;

import com.example.stuffmanagerapp.dto.MaintenanceDto;

import java.util.List;

public interface MaintenanceService {
    void save(MaintenanceDto dto);
    MaintenanceDto getById(Integer id);

    MaintenanceDto getByRequestId(Integer id);

    List<MaintenanceDto> getAll();
}
