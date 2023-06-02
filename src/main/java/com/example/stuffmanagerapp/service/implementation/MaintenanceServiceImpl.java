package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.MaintenanceDto;
import com.example.stuffmanagerapp.mapper.maintenance.MaintenanceListMapper;
import com.example.stuffmanagerapp.mapper.maintenance.MaintenanceMapper;
import com.example.stuffmanagerapp.model.repository.MaintenanceRepository;
import com.example.stuffmanagerapp.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final MaintenanceListMapper maintenanceListMapper;

    @Override
    public void save(MaintenanceDto dto) {
        maintenanceRepository.save(maintenanceMapper.toEntity(dto));
    }

    @Override
    public MaintenanceDto getById(Integer id) {
        return maintenanceMapper.toDto(maintenanceRepository.getReferenceById(id));
    }

    @Override
    public MaintenanceDto getByRequestId(Integer id) {
        return maintenanceMapper.toDto(maintenanceRepository.getByRequestId(id));
    }

    @Override
    public List<MaintenanceDto> getAll() {
        return maintenanceListMapper.toDtoList(maintenanceRepository.findAll());
    }
}
