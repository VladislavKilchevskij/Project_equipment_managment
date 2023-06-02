package com.example.stuffmanagerapp.controller.acts;

import com.example.stuffmanagerapp.dto.AcceptanceDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.service.AcceptanceService;
import com.example.stuffmanagerapp.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AcceptanceController {
    private final EmployeeService employeeService;
    private final AcceptanceService acceptanceService;

    @GetMapping("/acceptances")
    public String getAcceptances(Model model) {
        model.addAttribute("acceptances", acceptanceService.getAll());
        return "catalog/acts/listAcceptances";
    }
    @PostMapping("/acceptances/search")
    public String getSearch(@RequestParam("columnName") String columnName,
                                 @RequestParam("request") String request,
                                 Model model) {
        var result = acceptanceService.search(columnName, request);
        model.addAttribute("acceptances", result);
        return "catalog/acts/listAcceptances";
    }

    @GetMapping("/acceptance/add_header")
    public String addAcceptanceHeader(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("form", new AcceptanceDto());
        return "document/acceptance/addAcceptanceHeader";
    }

    @PostMapping("/acceptance/add_body")
    public String addAcceptanceBody(@ModelAttribute("form") AcceptanceDto newAcceptance, Model model){
        for (int i = 0; i < newAcceptance.getEquipmentRowAmount(); i++) {
            newAcceptance.getEquipment().add(new EquipmentDto());
        }
        model.addAttribute("form", newAcceptance);
        return "document/acceptance/addAcceptanceBody";
    }

    @PostMapping("/acceptance/save")
    public String saveAcceptance(@ModelAttribute("form") AcceptanceDto acceptance) {
        var file = acceptanceService.save(acceptance);
        return "redirect:/download/" + file.getName();
    }
}
