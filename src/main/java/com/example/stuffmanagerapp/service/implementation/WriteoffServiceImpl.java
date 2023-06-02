package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.dto.writeoff.WriteoffDto;
import com.example.stuffmanagerapp.mapper.writeoff.WriteoffListMapper;
import com.example.stuffmanagerapp.mapper.writeoff.WriteoffMapper;
import com.example.stuffmanagerapp.model.entity.Equipment;
import com.example.stuffmanagerapp.model.repository.EquipmentRepository;
import com.example.stuffmanagerapp.model.repository.WriteoffRepository;
import com.example.stuffmanagerapp.service.EmployeeService;
import com.example.stuffmanagerapp.service.EquipmentService;
import com.example.stuffmanagerapp.service.WriteoffService;
import com.example.stuffmanagerapp.util.excel.export.Exporter;
import com.example.stuffmanagerapp.util.excel.export.implementation.WriteoffExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WriteoffServiceImpl implements WriteoffService {

    private final WriteoffRepository writeoffRepository;
    private final EquipmentService equipmentService;
    private final EquipmentRepository equipmentRepository;
    private final EmployeeService employeeService;
    private final WriteoffMapper mapper;
    private final WriteoffListMapper listMapper;
    private final Exporter<WriteoffDto> exporter = new WriteoffExporter();
    public static final String WRITEOFFED = "Списано";

    @Override
    public WriteoffDto prepareWriteoffForm(Integer employeeId, String equipIds) {
        var writeoff = new WriteoffDto();
        var equipList = new ArrayList<EquipmentDto>();
        var employee = employeeService.getShortById(employeeId);
        String[] array;
        array = equipIds.split(" ");
        for (String s : array) {
            var equipment = equipmentService.getById(Integer.valueOf(s));
            equipList.add(equipment);
        }
        writeoff.setEquipment(equipList);
        writeoff.setEmployee(employee);
        return writeoff;
    }

    @Override
    public File save(WriteoffDto writeoffDto) {
        var writeoff = mapper.toEntity(writeoffDto);
        var totalCoast = new BigDecimal(0);
        var equipList = new ArrayList<Equipment>();
        for (Equipment equipment: writeoff.getEquipment()) {
            String writeoffReason = equipment.getWriteoffReason();
            equipment = equipmentRepository.getReferenceById(equipment.getId());
            equipment.setStatus(WRITEOFFED);
            equipment.setEmployee(null);
            equipment.setNextInspectionDate(null);
            equipment.setUsageEndDate(null);
            equipment.setWriteoffReason(writeoffReason);
            equipment.setWriteoff(writeoff);
            totalCoast = totalCoast.add(equipment.getCoast());
            equipList.add(equipment);
        }
        writeoff.setEquipment(equipList);
        writeoff.setEquipmentTotalCost(totalCoast);
        writeoffRepository.save(writeoff);
        writeoffDto.setEmployee(employeeService.getShortById(writeoffDto.getEmployee().getId()));
        File file = null;
        try {
            file = exporter.export(writeoff.getId(), writeoffDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public List<WriteoffDto> getAll() {
        return listMapper.toDtoList(writeoffRepository.findAll());
    }

    @Override
    public WriteoffDto getById(Integer id) {
        return mapper.toDto(writeoffRepository.getReferenceById(id));
    }

    @Override
    public void delete(WriteoffDto writeoffDto) {
        writeoffRepository.delete(mapper.toEntity(writeoffDto));
    }


}
