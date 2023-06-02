package com.example.stuffmanagerapp.controller.catalog;

import com.example.stuffmanagerapp.dto.employee.EmployeeDto;
import com.example.stuffmanagerapp.service.DepartmentService;
import com.example.stuffmanagerapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmployeesController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/employees")
    public String getEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "catalog/employees/listEmployees";
    }

    @PostMapping("/employees/search")
    public String getEmployees(@RequestParam("columnName") String columnName,
                               @RequestParam("request") String request,
                               Model model) {
        var result = employeeService.search(columnName, request);
        model.addAttribute("employees", result);
        return "catalog/employees/listEmployees";
    }

    @GetMapping("/employees/add")
    public String addEmployee(Model model) {
        model.addAttribute("newEmployee", new EmployeeDto());
        model.addAttribute("departments", departmentService.getAll());
        return "catalog/employees/addEmployee";
    }
    @PostMapping("/employees/save")
    public String saveNewEmployee(@ModelAttribute("newEmployee") EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/employees/{id}/edit")
    public String editEmployee(@PathVariable Integer id, Model model) {
        model.addAttribute("editEmployee", employeeService.getById(id));
        model.addAttribute("departments", departmentService.getAll());
        return "catalog/employees/editEmployee";
    }

    @PostMapping("/employees/update")
    public String updateEmployee(@ModelAttribute("editEmployee") EmployeeDto employeeDto) {
        employeeService.update(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/employees/{id}/delete")
    public String editEmployee(@PathVariable Integer id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
