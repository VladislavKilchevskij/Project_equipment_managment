package com.example.stuffmanagerapp.service;


import com.example.stuffmanagerapp.dto.RepairHistoryDto;

import java.util.List;

public interface RepairService {

    void save(RepairHistoryDto dto);
    RepairHistoryDto getById(Integer id);
    List<RepairHistoryDto> getAll();
}
