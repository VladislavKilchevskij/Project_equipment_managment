package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.AcceptanceDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.mapper.acceptance.AcceptanceListMapper;
import com.example.stuffmanagerapp.mapper.acceptance.AcceptanceMapper;
import com.example.stuffmanagerapp.model.entity.Equipment;
import com.example.stuffmanagerapp.model.repository.AcceptanceRepository;
import com.example.stuffmanagerapp.service.AcceptanceService;
import com.example.stuffmanagerapp.service.EmployeeService;
import com.example.stuffmanagerapp.util.Calculations;
import com.example.stuffmanagerapp.util.excel.export.Exporter;
import com.example.stuffmanagerapp.util.excel.export.implementation.AcceptanceExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcceptanceServiceImpl implements AcceptanceService {
    private final AcceptanceRepository acceptanceRepository;
    private final EmployeeService employeeService;
    private final AcceptanceListMapper listMapper;
    private final AcceptanceMapper mapper;
    private final Exporter<AcceptanceDto> exporter = new AcceptanceExporter();

    private static final String READY = "Исправно";

    @Override
    public File save(AcceptanceDto acceptanceDto) {
        List<EquipmentDto> eqipmentWithAmount = acceptanceDto.getEquipment();
        acceptanceDto.setEquipment(fillByAmount(acceptanceDto.getEquipment()));
        var acceptance = mapper.toEntity(acceptanceDto);
        BigDecimal totalCoast = new BigDecimal(0);
        for (Equipment equipment: acceptance.getEquipment()) {
            equipment.setAcceptance(acceptance);
            equipment.setUsageEndDate(Calculations.calculateUsageEndDate(equipment.getUsageTerm()));
            equipment.setStatus(READY);
            totalCoast = totalCoast.add(equipment.getCoast());
        }
        acceptance.setTotalCoast(totalCoast);
        acceptanceRepository.save(acceptance);

        acceptanceDto.setEquipment(eqipmentWithAmount);
        acceptanceDto.setEmployee(employeeService.getShortById(acceptanceDto.getEmployee().getId()));
        File file = null;
        try {
            file = exporter.export(acceptance.getId(), acceptanceDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public List<AcceptanceDto> getAll() {
        return listMapper.toDtoList(acceptanceRepository.findAll());
    }

    @Override
    public AcceptanceDto getById(Integer id) {
        return mapper.toDto(acceptanceRepository.getReferenceById(id));
    }

    @Override
    public List<AcceptanceDto> search(String columnName, String request) {
        List<AcceptanceDto> result = new ArrayList<>();
        switch (columnName) {
            case "id" ->
                    result = listMapper.toDtoList(acceptanceRepository.findAllById(Integer.valueOf(request)));
            case "provider" ->
                    result = listMapper.toDtoList(acceptanceRepository.findAllByProviderContainingIgnoreCase(request));
            case "coast" ->
                    result = listMapper.toDtoList(acceptanceRepository.findAllByTotalCoast(new BigDecimal(request)));
            case "employee" -> {
                String[] array = request.split(" ");
                result = getAll().stream().filter(item ->
                                item.getEmployee().getSurname().equals(array[0])
                                        && item.getEmployee().getFirstName().equals(array[1])
                                        && item.getEmployee().getPatronymic().equals(array[2]))
                        .collect(Collectors.toList());
            }
            case "acceptDate" -> {
                String[] dateArray = request.split("-");
                String validRequest = String.format("%s-%s-%s", dateArray[2], dateArray[1], dateArray[0]);
                result = listMapper.toDtoList(acceptanceRepository.findAllByAcceptanceDate(Date.valueOf(validRequest)));
            }
        }
        return result;
    }

    @Override
    public void delete(AcceptanceDto acceptanceDto) {
        acceptanceRepository.delete(mapper.toEntity(acceptanceDto));
    }

    private List<EquipmentDto> fillByAmount(List<EquipmentDto> list) {
        List<EquipmentDto> filledList = new ArrayList<>();
        for (EquipmentDto dto : list) {
            for (int i = 1; i <= dto.getAmount(); i++) {
                EquipmentDto equipmentDto = new EquipmentDto();
                equipmentDto.setCategory(dto.getCategory());
                equipmentDto.setEquipmentName(dto.getEquipmentName());
                equipmentDto.setCoast(dto.getCoast());
                equipmentDto.setInspectionTerm(dto.getInspectionTerm());
                equipmentDto.setUsageTerm(dto.getUsageTerm());
                filledList.add(equipmentDto);
            }
        }
        return filledList;
    }
}