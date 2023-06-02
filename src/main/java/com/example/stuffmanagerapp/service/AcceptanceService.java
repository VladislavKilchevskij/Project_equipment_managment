package com.example.stuffmanagerapp.service;

import com.example.stuffmanagerapp.dto.AcceptanceDto;

import java.io.File;
import java.util.List;

public interface AcceptanceService {
    File save(AcceptanceDto acceptanceDto);
    List<AcceptanceDto> getAll();
    AcceptanceDto getById(Integer id);

    List<AcceptanceDto> search(String columnName, String request);

    void delete(AcceptanceDto acceptanceDto);
}
