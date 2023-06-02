package com.example.stuffmanagerapp.service;

import com.example.stuffmanagerapp.dto.writeoff.WriteoffDto;

import java.io.File;
import java.util.List;

public interface WriteoffService {
    File save(WriteoffDto writeoffDto);
    List<WriteoffDto> getAll();
    WriteoffDto getById(Integer id);
    void delete(WriteoffDto writeoffDto);

    WriteoffDto prepareWriteoffForm(Integer employeeId, String equipIds);
}
