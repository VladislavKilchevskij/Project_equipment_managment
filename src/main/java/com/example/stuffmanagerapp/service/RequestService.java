package com.example.stuffmanagerapp.service;

import com.example.stuffmanagerapp.dto.RequestDto;

import java.io.File;
import java.util.List;

public interface RequestService {
    File save(RequestDto dto);

    List<RequestDto> getAllUnfinishedRequests();

    List<RequestDto> getAllFinishedRequests();

    RequestDto getById(Integer id);
    List<RequestDto> getAll();

    List<RequestDto> search(String columnName, String request);

    List<RequestDto> maintenanceSearch(String columnName, String request);
}
