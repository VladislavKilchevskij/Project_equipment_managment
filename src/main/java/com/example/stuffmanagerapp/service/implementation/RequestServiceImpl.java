package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.RequestDto;
import com.example.stuffmanagerapp.mapper.request.RequestListMapper;
import com.example.stuffmanagerapp.mapper.request.RequestMapper;
import com.example.stuffmanagerapp.model.entity.RepairHistory;
import com.example.stuffmanagerapp.model.repository.*;
import com.example.stuffmanagerapp.service.EmployeeService;
import com.example.stuffmanagerapp.service.RequestService;
import com.example.stuffmanagerapp.util.excel.export.Exporter;
import com.example.stuffmanagerapp.util.excel.export.implementation.MaintenanceExporter;
import com.example.stuffmanagerapp.util.excel.export.implementation.RequestExporter;
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
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final RepairHistoryRepository repairHistoryRepository;
    private final EquipmentRepository equipmentRepository;
    private final EmployeeService employeeService;
    private final RequestMapper requestMapper;
    private final RequestListMapper requestListMapper;
    private final Exporter<RequestDto> exporter = new RequestExporter();
    private final Exporter<RequestDto> maintenanceExporter = new MaintenanceExporter();
    private static final String REQUEST = "Заявка";
    private static final String READY = "Исправно";


    @Override
    public File save(RequestDto dto) {
        var request = requestMapper.toEntity(dto);
        File file = null;
        if (request.getMaintenance() == null) {
            for (RepairHistory repair: request.getHistoryList()) {
                equipmentRepository.changeStatus(repair.getEquipment().getId(), REQUEST);
                repair.setRequest(request);
            }
            requestRepository.save(request);
            dto.setEmployee(employeeService.getShortById(dto.getEmployee().getId()));
            try {
                file = exporter.export(request.getId(), dto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return file;
        } else {
            var maintenance = request.getMaintenance();
            var totalServiceCoast = new BigDecimal(0);
            for (RepairHistory repair: request.getHistoryList()) {
                equipmentRepository.changeStatus(repair.getEquipment().getId(), READY);
                repairHistoryRepository.updateMaintenanceInfo(repair.getRenderedService(), repair.getServiceCoast(), repair.getId());
                totalServiceCoast = totalServiceCoast.add(repair.getServiceCoast());
            }
            maintenance.setTotalServiceCost(totalServiceCoast);
            maintenance.setRequest(request);
            maintenanceRepository.save(maintenance);
            dto.getMaintenance().setEmployee(employeeService.getShortById(dto.getMaintenance().getEmployee().getId()));
            try {
                file = maintenanceExporter.export(maintenance.getId(), dto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return file;
        }
    }

    @Override
    public List<RequestDto> getAll() {
        return requestListMapper.toDtoList(requestRepository.findAll());
    }

    @Override
    public List<RequestDto> getAllUnfinishedRequests() {
        return requestListMapper.toDtoList(requestRepository.findAllByMaintenanceIs(null));
    }

    @Override
    public List<RequestDto> getAllFinishedRequests() {
        return requestListMapper.toDtoList(requestRepository.findAllByMaintenanceIsNotNull());
    }

    @Override
    public RequestDto getById(Integer id) {
        return requestMapper.toDto(requestRepository.getReferenceById(id));
    }

    @Override
    public List<RequestDto> search(String columnName, String request) {
        List<RequestDto> result = new ArrayList<>();
        switch (columnName) {
            case "id" ->
                    result = getAllUnfinishedRequests().stream().filter(item -> item.getId().equals(Integer.valueOf(request)))
                            .collect(Collectors.toList());
            case "reqDate" -> {
                String[] dateArray = request.split("-");
                String validRequest = String.format("%s-%s-%s", dateArray[2], dateArray[1], dateArray[0]);
                result = getAllUnfinishedRequests().stream().filter(item -> item.getRegDate().equals(Date.valueOf(validRequest)))
                        .collect(Collectors.toList());
            }
            case "reqEmployee" -> {
                String[] array = request.split(" ");
                result = getAllUnfinishedRequests().stream().filter(item ->
                                item.getEmployee().getSurname().equals(array[0])
                                        && item.getEmployee().getFirstName().equals(array[1])
                                        && item.getEmployee().getPatronymic().equals(array[2]))
                        .collect(Collectors.toList());
            }
        }
        return result;
    }
    @Override
    public List<RequestDto> maintenanceSearch(String columnName, String request) {
        List<RequestDto> result = new ArrayList<>();
        switch (columnName) {
            case "id" ->
                    result = requestListMapper.toDtoList(requestRepository.findAllByMaintenanceId(Integer.valueOf(request)));
            case "maintDate" -> {
                String[] dateArray = request.split("-");
                String validRequest = String.format("%s-%s-%s", dateArray[2], dateArray[1], dateArray[0]);
                result = requestListMapper.toDtoList(requestRepository.findAllByMaintenanceServiceDate(Date.valueOf(validRequest)));
            }
            case "maintEmployee" -> {
                String[] array = request.split(" ");
                result = getAllFinishedRequests().stream().filter(item ->
                                item.getMaintenance().getEmployee().getSurname().equals(array[0])
                                        && item.getMaintenance().getEmployee().getFirstName().equals(array[1])
                                        && item.getMaintenance().getEmployee().getPatronymic().equals(array[2]))
                        .collect(Collectors.toList());
            }
            case "coast" ->
                    result = requestListMapper.toDtoList(requestRepository.findAllByMaintenanceTotalServiceCost(new BigDecimal(request)));
            case "reqId" ->
                    result = getAllFinishedRequests().stream().filter(item -> item.getId().equals(Integer.valueOf(request)))
                            .collect(Collectors.toList());
            case "reqDate" -> {
                String[] dateArray = request.split("-");
                String validRequest = String.format("%s-%s-%s", dateArray[2], dateArray[1], dateArray[0]);
                result = getAllFinishedRequests().stream().filter(item -> item.getRegDate().equals(Date.valueOf(validRequest)))
                        .collect(Collectors.toList());
            }
            case "reqEmployee" -> {
                String[] array = request.split(" ");
                result = getAllFinishedRequests().stream().filter(item ->
                                item.getEmployee().getSurname().equals(array[0])
                                        && item.getEmployee().getFirstName().equals(array[1])
                                        && item.getEmployee().getPatronymic().equals(array[2]))
                        .collect(Collectors.toList());
            }
        }
        return result;
    }
}