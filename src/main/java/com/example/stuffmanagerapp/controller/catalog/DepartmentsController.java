package com.example.stuffmanagerapp.controller.catalog;

import com.example.stuffmanagerapp.dto.DepartmentShortDto;
import com.example.stuffmanagerapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@RequiredArgsConstructor
public class DepartmentsController {
    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public String getDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllWithChief());
        return "catalog/departments/listDepartments";
    }

    @PostMapping("/departments/search")
    public String searchDepartment(@RequestParam("columnName") String columnName,
                                   @RequestParam("request") String request,
                                   Model model) {
        var result = departmentService.search(columnName, request);
        model.addAttribute("departments", result);
        return "catalog/departments/listDepartments";
    }

    @GetMapping("/departments/add")
    public String addDepartment(Model model) {
        model.addAttribute("newDepartment", new DepartmentShortDto());
        return "catalog/departments/addDepartment";
    }
    @PostMapping("/departments/save")
    public String saveNewDepartment(@ModelAttribute("newDepartment") DepartmentShortDto departmentShortDto) {
        departmentService.save(departmentShortDto);
        return "redirect:/departments";
    }

    @GetMapping("/departments/{id}/edit")
    public String editDepartment(@PathVariable Integer id, Model model) {
        model.addAttribute("editDepartment", departmentService.getById(id));
        return "catalog/departments/editDepartment";
    }

    @PostMapping("/departments/update")
    public String updateDepartment(@ModelAttribute("editDepartment") DepartmentShortDto departmentShortDto) {
        departmentService.update(departmentShortDto);
        return "redirect:/departments";
    }

    @GetMapping("/departments/{id}/delete")
    public String deleteDepartment(@PathVariable Integer id,
                                 Model model) {
        var isDeleted = departmentService.delete(id);
        if (!isDeleted) {
            model.addAttribute("errMsg", "Отдел не может быть удалён, пока за ним закреплены сотрудники!");
            return getDepartments(model);
        }
        return "redirect:/departments";
    }
}
