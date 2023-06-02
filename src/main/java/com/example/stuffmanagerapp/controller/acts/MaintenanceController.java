package com.example.stuffmanagerapp.controller.acts;

import com.example.stuffmanagerapp.dto.MaintenanceDto;
import com.example.stuffmanagerapp.dto.RequestDto;
import com.example.stuffmanagerapp.service.EmployeeService;
import com.example.stuffmanagerapp.service.MaintenanceService;
import com.example.stuffmanagerapp.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MaintenanceController {
    private final RequestService requestService;
    private final EmployeeService employeeService;
    private final MaintenanceService maintenanceService;

    @GetMapping("/maintenances")
    public String getMaintenances(Model model) {
        model.addAttribute("requests", requestService.getAllFinishedRequests());
        return "catalog/acts/listMaintenances";
    }

    @PostMapping("/maintenances/search")
    public String getSearch(@RequestParam("columnName") String columnName,
                            @RequestParam("request") String request,
                            Model model) {
        var result = requestService.maintenanceSearch(columnName, request);
        model.addAttribute("requests", result);
        return "catalog/acts/listMaintenances";
    }

    @GetMapping("/maintenance/{requestId}/addMaintenance")
    public String addMaintenance(@PathVariable Integer requestId, Model model) {
        var request = requestService.getById(requestId);
        request.setMaintenance(new MaintenanceDto());
        model.addAttribute("form", request);
        model.addAttribute("employees", employeeService.getAll());
        return "document/maintenance";
    }

    @PostMapping("/maintenance/save")
    public String saveMaintenance(@ModelAttribute("form")RequestDto requestDto) {
        var file = requestService.save(requestDto);
        return "redirect:/download/" + file.getName();
    }
}
