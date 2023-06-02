package com.example.stuffmanagerapp.controller;

import com.example.stuffmanagerapp.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
class AdviceGlobal {
    private final EquipmentService equipmentService;

    @ModelAttribute
    void addNotificationData(Model model) {
        var notifications = equipmentService.getExpiredInspectionTerm();
        var counter = notifications.size();
        model.addAttribute("notifications", notifications);
        model.addAttribute("counter", counter);
        model.addAttribute("hiddenAmount", counter - 7);
    }
}
