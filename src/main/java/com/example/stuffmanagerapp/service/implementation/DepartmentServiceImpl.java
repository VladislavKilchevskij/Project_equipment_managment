package com.example.stuffmanagerapp.service.implementation;

import com.example.stuffmanagerapp.dto.DepartmentDto;
import com.example.stuffmanagerapp.dto.DepartmentShortDto;
import com.example.stuffmanagerapp.dto.employee.EmployeeChiefDto;
import com.example.stuffmanagerapp.mapper.department.DepartmentListMapper;
import com.example.stuffmanagerapp.mapper.department.DepartmentShortListMapper;
import com.example.stuffmanagerapp.mapper.department.DepartmentShortMapper;
import com.example.stuffmanagerapp.model.repository.DepartmentRepository;
import com.example.stuffmanagerapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentShortMapper shortMapper;
    private final DepartmentShortListMapper shortListMapper;
    private final DepartmentListMapper listMapper;

    @Override
    public void save(DepartmentShortDto departmentShortDto) {
        departmentRepository.save(shortMapper.toEntity(departmentShortDto));
    }

    @Override
    public List<DepartmentShortDto> getAll() {
        return shortListMapper.toDtoList(departmentRepository.findAll());
    }

    @Override
    public List<DepartmentDto> getAllWithChief() {
        var departments = listMapper.toDtoList(departmentRepository.findAll());
        for (DepartmentDto departmentDto: departments) {
            departmentDto.setEmployees(departmentDto.getEmployees()
                    .stream().filter(EmployeeChiefDto::isChief).collect(Collectors.toList()));
        }
        return departments;
    }

    @Override
    public DepartmentShortDto getById(Integer id) {
        return shortMapper.toDto(departmentRepository.getReferenceById(id));
    }

    @Override
    public List<DepartmentDto> search(String columnName, String request) {
        List<DepartmentDto> result = new ArrayList<>();
        switch (columnName) {
            case "id" ->
                result = listMapper.toDtoList(departmentRepository.findAllById(Integer.valueOf(request)));
            case "departmentName" ->
                result = listMapper.toDtoList(departmentRepository.findAllByDepartmentNameContainingIgnoreCase(request));
            case "employee" -> {
                String[] array = request.split(" ");
                result = getAllWithChief().stream().filter(item ->
                                item.getEmployees().get(0).getSurname().equals(array[0])
                                && item.getEmployees().get(0).getFirstName().equals(array[1])
                                && item.getEmployees().get(0).getPatronymic().equals(array[2]))
                        .collect(Collectors.toList());
            }
            case "phoneNumber" ->
                result = listMapper.toDtoList(departmentRepository.findAllByPhoneNumberContaining(request));
            case "floor" ->
                result = listMapper.toDtoList(departmentRepository.findAllByFloor(Integer.valueOf(request)));
        }
        return result;
    }

    @Override
    public void update(DepartmentShortDto departmentShortDto) {
        departmentRepository.updateDepartmentInfo(shortMapper.toEntity(departmentShortDto));
    }

    @Override
    public boolean delete(Integer id) {
        var department = departmentRepository.getReferenceById(id);
        if (department.getEmployees().size() > 0) {
            return false;
        }
        departmentRepository.deleteById(id);
        return true;
    }
}
