package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.employee.EmployeeDto;
import com.example.stuffmanagerapp.dto.employee.EmployeeGetEquipmentDto;
import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import com.example.stuffmanagerapp.mapper.employee.EmployeeEquipmentMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeListMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeShortMapper;
import com.example.stuffmanagerapp.model.repository.EmployeeRepository;
import com.example.stuffmanagerapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private final EmployeeShortMapper employeeShortMapper;
    private final EmployeeEquipmentMapper employeeEquipmentMapper;
    private final EmployeeListMapper listMapper;

    @Override
    public void save(EmployeeDto employeeDto) {
        employeeRepository.save(mapper.toEntity(employeeDto));
    }

    @Override
    public void save(EmployeeGetEquipmentDto employeeDto) {
        employeeRepository.save(employeeEquipmentMapper.toEntity(employeeDto));
    }

    @Override
    public List<EmployeeDto> getAll() {
        return listMapper.toDtoList(employeeRepository.findAll());
    }

    @Override
    public EmployeeDto getById(Integer id) {
        return mapper.toDto(employeeRepository.getReferenceById(id));
    }

    @Override
    public EmployeeShortDto getShortById(Integer id) {
        return employeeShortMapper.toDto(employeeRepository.getReferenceById(id));
    }

    @Override
    public List<EmployeeDto> search(String columnName, String request) {
        List<EmployeeDto> result = new ArrayList<>();
        switch (columnName) {
            case "id" ->
                    result = listMapper.toDtoList(employeeRepository.findAllById(Integer.valueOf(request)));
            case "departmentName" ->
                    result = listMapper.toDtoList(employeeRepository.findAllByDepartmentDepartmentName(request));
            case "employee" -> {
                String[] array = request.split(" ");
                result = getAll().stream().filter(item ->
                                item.getSurname().equals(array[0])
                                        && item.getFirstName().equals(array[1])
                                        && item.getPatronymic().equals(array[2]))
                        .collect(Collectors.toList());
            }
            case "position" ->
                    result = listMapper.toDtoList(employeeRepository.findAllByPositionContainingIgnoreCase(request));
        }
        return result;
    }

    @Override
    public void update(EmployeeDto employeeDto) {
        employeeRepository.updateEmployeeInfo(mapper.toEntity(employeeDto));
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }
}
