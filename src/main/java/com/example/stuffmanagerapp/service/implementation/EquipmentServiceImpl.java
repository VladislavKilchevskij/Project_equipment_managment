package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentShortDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentTableDto;
import com.example.stuffmanagerapp.mapper.equipment.*;
import com.example.stuffmanagerapp.model.entity.Equipment;
import com.example.stuffmanagerapp.model.repository.EquipmentRepository;
import com.example.stuffmanagerapp.service.EquipmentService;
import com.example.stuffmanagerapp.util.Calculations;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentTableMapper equipmentTableMapper;
    private final EquipmentTableListMapper listMapper;
    private final EquipmentShortListMapper equipmentShortListMapper;
    private final EquipmentMapper equipmentMapper;
    private static final String WRITEOFFED = "Списано";
    private static final String REPAIRING = "На обслуживании";
    private static final String READY = "Исправно";

    @Override
    public void save(EquipmentTableDto dto) {
        equipmentRepository.save(equipmentTableMapper.toEntity(dto));
    }

    @Override
    public List<EquipmentTableDto> getAll() {
        var list = equipmentRepository.findAll();
        return listMapper.toDtoList(list);
    }

    @Override
    public Page<EquipmentTableDto> getAllCurrentPaginatedAndSorted(int pageNo, String sortField, String sortDirection) {
        int pageSize = 10;
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Equipment> equipment = equipmentRepository.findAllByStatusIsNot(WRITEOFFED, pageable);
        return equipment.map(equipmentTableMapper::toDto);
    }

    @Override
    public List<EquipmentTableDto> getAllWriteoffed() {
        return listMapper.toDtoList(equipmentRepository.findAllByStatusIs(WRITEOFFED));
    }

    @Override
    public List<EquipmentShortDto> getExpiredInspectionTerm() {
        var currentDate = Calculations.getCurrentTime();
        List<EquipmentShortDto> equipment;
        if (Calculations.isCurrentDateNearQuarter()) {
            equipment = equipmentShortListMapper.toDtoList(equipmentRepository.findAllExpired("^((20[0-9]{2})-(0[147]{1}|10)-(0[1-9]|1[0-9]|20))$", currentDate, Calculations.addWeek()));
            for (EquipmentShortDto dto: equipment) {
                var days = Calculations.getDaysBetween(currentDate, dto.getNextInspectionDate());
                if (days > 7) {
                    dto.setMessage("След. дата проф. осмотра выпадает на отчётный срок!");
                } else {
                    dto.setMessage("Дней до истечения срока осмотра: " + days);
                }
            }
        } else {
            equipment = equipmentShortListMapper.toDtoList(equipmentRepository.findAllByNextInspectionDateBetween(currentDate, Calculations.addWeek()));
            for (EquipmentShortDto dto: equipment) {
                var days = Calculations.getDaysBetween(currentDate, dto.getNextInspectionDate());
                dto.setMessage("Дней до истечения срока осмотра:" + days);
            }
        }
        return equipment;
    }

    @Override
    public List<EquipmentShortDto> getAllByEmployeeId(Integer id) {
        return equipmentShortListMapper.toDtoList(equipmentRepository.findAllByEmployeeId(id));
    }

    @Override
    public EquipmentDto getById(Integer id) {
        return equipmentMapper.toDto(equipmentRepository.getReferenceById(id));
    }

    @Override
    public String updateEquipmentUser(Integer employeeId, String equipIds) {
        String errLog = "";
        String[] array;
        array = equipIds.split(" ");
        if (employeeId == 0) {
            for (String s : array) {
                var equip = equipmentRepository.getReferenceById(Integer.valueOf(s));
                equipmentRepository.unbindEquipmentUser(Integer.valueOf(s));
                if (equip.getStatus().equals("Заявка")) {
                    equipmentRepository.changeStatus(Integer.valueOf(s), REPAIRING);
                }
            }
        } else {
            for (String s : array) {
                if (equipmentRepository.existsByIdAndEmployeeIsNotNull(Integer.valueOf(s)) || equipmentRepository.existsByIdAndStatusIsNot(Integer.valueOf(s), READY)) {
                    errLog = errLog.concat(s + " ");
                } else {
                    var equip = equipmentRepository.getReferenceById(Integer.valueOf(s));
                    equipmentRepository.bindEquipmentUser(Integer.valueOf(s),
                                                            Calculations.calculateNextInspectionDate(equip.getInspectionTerm()),
                                                            employeeId);
                }
            }
            if (!errLog.isEmpty()) {
                errLog = "Инв. номера: " + errLog;
            }
        }
        return errLog;
    }

    @Override
    public List<EquipmentTableDto> search(String columnName, String request, boolean isActual) {
        List<EquipmentTableDto> result = new ArrayList<>();
        if (isActual) {
            switch (columnName) {
                case "id" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndId(WRITEOFFED, Integer.valueOf(request)));
                case "category" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndCategory(WRITEOFFED, request));
                case "equipmentName" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndEquipmentNameContainingIgnoreCase(WRITEOFFED, request));
                case "coast" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndCoast(WRITEOFFED, new BigDecimal(request)));
                case "employee" -> {
                    if (request.equalsIgnoreCase("На складе")) {
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndEmployeeIsNull(WRITEOFFED));
                        break;
                    }
                    String[] array = request.split(" ");
                    result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndEmployee(WRITEOFFED, array[0], array[1], array[2]));
                }
                case "status" -> result = listMapper.toDtoList(equipmentRepository.findAllByStatusIs(request));
                case "nextInspectionDate" -> {
                    String[] dateArray = request.split("-");
                    String validRequest = String.format("%s-%s-%s", dateArray[2], dateArray[1], dateArray[0]);
                    result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndNextInspectionDate(WRITEOFFED, Date.valueOf(validRequest)));
                }
                case "usageEndDate" -> {
                    String[] dateEndArray = request.split("-");
                    String validRequest2 = String.format("%s-%s-%s", dateEndArray[2], dateEndArray[1], dateEndArray[0]);
                    result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsNotAndUsageEndDate(WRITEOFFED, Date.valueOf(validRequest2)));
                }
            }
        } else {
            switch (columnName) {
                case "id" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsAndId(WRITEOFFED, Integer.valueOf(request)));
                case "category" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsAndCategory(WRITEOFFED, request));
                case "equipmentName" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsAndEquipmentNameContainingIgnoreCase(WRITEOFFED, request));
                case "employee" -> {
                    String[] array = request.split(" ");
                    result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsAndWriteoffEmployee(WRITEOFFED, array[0], array[1], array[2]));
                }
                case "writeoffDate" -> {
                    String[] dateOffArray = request.split("-");
                    String validRequest3 = String.format("%s-%s-%s", dateOffArray[2], dateOffArray[1], dateOffArray[0]);
                    result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsAndWriteoffDate(WRITEOFFED, Date.valueOf(validRequest3)));
                }
                case "writeoffReason" ->
                        result = listMapper.toDtoList(equipmentRepository.findAllByStatusIsAndWriteoffReasonContaining(WRITEOFFED, request));
            }
        }
        return result;
    }
}