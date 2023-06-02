package com.example.stuffmanagerapp.controller.catalog;

import com.example.stuffmanagerapp.dto.equipment.EquipmentTableDto;
import com.example.stuffmanagerapp.service.EmployeeService;
import com.example.stuffmanagerapp.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EquipmentController {
    private static final String SORTFIELD = "id";
    private static final String ASC = "asc";
    private static final String DESC = "desc";
    private final EquipmentService equipmentService;
    private final EmployeeService employeeService;

    @GetMapping("/equipment")
    public String getEquipment(Model model) {
        return getPaginatedAndSortedEquipment(1, SORTFIELD, ASC, model);
    }

    @GetMapping("/equipment/page/{pageNo}")
    public String getPaginatedAndSortedEquipment(@PathVariable(value = "pageNo") int pageNo,
                                                 @RequestParam("sortField") String sortField,
                                                 @RequestParam("sortDir") String sortDir,
                                                 Model model) {
        Page<EquipmentTableDto> page = equipmentService.getAllCurrentPaginatedAndSorted(pageNo, sortField, sortDir);
        List<EquipmentTableDto> equipment = page.getContent();
        model.addAttribute("equipment", equipment);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals(ASC) ? DESC : ASC);
        model.addAttribute("employees", employeeService.getAll());
        return "catalog/equipment/listEquipment";
    }

    @PostMapping("/equipment/search")
    public String getSearchResult(@RequestParam("columnName") String columnName,
                                  @RequestParam("request") String request,
                                  Model model) {
        model.addAttribute("equipment", equipmentService.search(columnName, request, true));
        model.addAttribute("employees", employeeService.getAll());
        return "catalog/equipment/listEquipment";
    }

    @GetMapping("/equipment/archive")
    public String getEquipmentArchive(Model model) {
        model.addAttribute("equipment", equipmentService.getAllWriteoffed());
        return "catalog/equipment/listArchiveEquipment";
    }

    @PostMapping("/equipment/archive/search")
    public String getArchiveSearchResult(@RequestParam("columnName") String columnName,
                                  @RequestParam("request") String request,
                                  Model model) {
        model.addAttribute("equipment", equipmentService.search(columnName, request, false));
        return "catalog/equipment/listArchiveEquipment";
    }

    @PostMapping("/equipment/setEmployee")
    public String setEquipmentToEmployee(@RequestParam("equipString") String equipString,
                                         @RequestParam("employeeId") Integer employeeId,
                                         Model model) {
        var errLog = equipmentService.updateEquipmentUser(employeeId, equipString);
        if (!errLog.isEmpty()) {
            model.addAttribute("errLog", errLog);
            return getEquipment(model);
        }
        return "redirect:/equipment";
    }
}
