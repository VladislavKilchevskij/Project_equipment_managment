package com.example.stuffmanagerapp.controller;

import com.example.stuffmanagerapp.util.excel.edit.Editor;
import com.example.stuffmanagerapp.util.excel.edit.implementation.*;
import com.example.stuffmanagerapp.util.excel.entity.descendants.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {
    private final Editor<AcceptanceDocument> acceptanceEditor = new AcceptanceEditor();
    private final Editor<MaintenanceDocument> maintenanceEditor = new MaintenanceEditor();
    private final Editor<WriteoffDocument> writeoffEditor = new WriteoffEditor();

    @GetMapping("/settings/editActPriema")
    public String editActPriema(Model model) {
        model.addAttribute("form", acceptanceEditor.getDocument());
        return "settings/editActPriema";
    }

    @PostMapping("/settings/editActPriema/save")
    public String saveActPriema(@ModelAttribute("form") AcceptanceDocument acceptanceDocument) {
        acceptanceEditor.updateDocument(acceptanceDocument);
        return "settings/editActPriema";
    }

    @GetMapping("/settings/editActRemonta")
    public String editActRemonta(Model model) {
        model.addAttribute("form", maintenanceEditor.getDocument());
        return "settings/editActRemonta";
    }

    @PostMapping("/settings/editActRemonta/save")
    public String saveActRemonta(@ModelAttribute("form") MaintenanceDocument maintenanceDocument) {
        maintenanceEditor.updateDocument(maintenanceDocument);
        return "settings/editActRemonta";
    }

    @GetMapping("/settings/editActSpisania")
    public String editActSpisania(Model model) {
        model.addAttribute("form", writeoffEditor.getDocument());
        return "settings/editActSpisania";
    }

    @PostMapping("/settings/editActSpisania/save")
    public String saveActSpisania(@ModelAttribute("form") WriteoffDocument writeoffDocument) {
        writeoffEditor.updateDocument(writeoffDocument);
        return "settings/editActSpisania";
    }
}
