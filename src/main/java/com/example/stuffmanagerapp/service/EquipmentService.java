package com.example.stuffmanagerapp.service;

import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentShortDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentTableDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EquipmentService {
    void save(EquipmentTableDto dto);
    List<EquipmentTableDto> getAll();
    Page<EquipmentTableDto> getAllCurrentPaginatedAndSorted(int pageNo, String sortField, String sortDirection);

    List<EquipmentTableDto> getAllWriteoffed();

    List<EquipmentShortDto> getExpiredInspectionTerm();
    List<EquipmentShortDto> getAllByEmployeeId(Integer id);
    EquipmentDto getById(Integer id);
    String updateEquipmentUser(Integer employeeId, String equipIds);
    List<EquipmentTableDto> search(String columnName, String request, boolean isActual);
}
