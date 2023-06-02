package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.RepairHistoryDto;
import com.example.stuffmanagerapp.mapper.repairHistory.RepairListMapper;
import com.example.stuffmanagerapp.mapper.repairHistory.RepairMapper;
import com.example.stuffmanagerapp.model.repository.RepairHistoryRepository;
import com.example.stuffmanagerapp.service.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairServiceImpl implements RepairService {

    private final RepairHistoryRepository repairRepository;
    private final RepairMapper repairMapper;
    private final RepairListMapper repairListMapper;

    @Override
    public void save(RepairHistoryDto dto) {
        repairRepository.save(repairMapper.toEntity(dto));
    }

    @Override
    public RepairHistoryDto getById(Integer id) {
        return repairMapper.toDto(repairRepository.getReferenceById(id));
    }

    @Override
    public List<RepairHistoryDto> getAll() {
        return repairListMapper.toDtoList(repairRepository.findAll());
    }
}
