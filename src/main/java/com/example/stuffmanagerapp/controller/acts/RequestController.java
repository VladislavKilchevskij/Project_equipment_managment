package com.example.stuffmanagerapp.controller.acts;

import com.example.stuffmanagerapp.dto.RepairHistoryDto;
import com.example.stuffmanagerapp.dto.RequestDto;
import com.example.stuffmanagerapp.service.EmployeeService;
import com.example.stuffmanagerapp.service.EquipmentService;
import com.example.stuffmanagerapp.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final EmployeeService employeeService;
    private final EquipmentService equipmentService;

    @GetMapping("/requests")
    public String getRequests(Model model) {
        model.addAttribute("requests", requestService.getAllUnfinishedRequests());
        return "catalog/acts/listRequests";
    }

    @PostMapping("/requests/search")
    public String getRequests(@RequestParam("columnName") String columnName,
                              @RequestParam("request") String request,
                              Model model) {
        var result = requestService.search(columnName, request);
        model.addAttribute("requests", result);
        return "catalog/acts/listRequests";
    }

    @GetMapping("/request/add_header")
    public String getRequestHeader(Model model) {
        model.addAttribute("form", new RequestDto());
        model.addAttribute("employees", employeeService.getAll());
        return "document/request/requestHeader";
    }

    @PostMapping("/request/add_body")
    public String addRequestBody(@ModelAttribute("form") RequestDto request, Model model) {
        for (int i = 0; i < request.getRowAmount(); i++) {
            request.getHistoryList().add(new RepairHistoryDto());
        }
        model.addAttribute("equipmentList", equipmentService.getAllByEmployeeId(request.getEmployee().getId()));
        model.addAttribute("form", request);
        model.addAttribute("employees", employeeService.getAll());
        return "document/request/requestBody";
    }

    @PostMapping("/request/save")
    public String saveRequest(@ModelAttribute("form") RequestDto request) {
        var file = requestService.save(request);
        return "redirect:/download/" + file.getName();
    }

}
