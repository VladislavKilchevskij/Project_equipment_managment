package com.example.stuffmanagerapp.controller.acts;

import com.example.stuffmanagerapp.dto.writeoff.WriteoffDto;
import com.example.stuffmanagerapp.service.WriteoffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class WriteoffController {
    private final WriteoffService writeoffService;

    @PostMapping("/writeoff/initWriteoff")
    public String prepareWriteoff(@RequestParam("equipString") String equipString,
                              @RequestParam("employeeId") Integer employeeId,
                              Model model) {
        var writeoff = writeoffService.prepareWriteoffForm(employeeId, equipString);
        model.addAttribute("form", writeoff);
        return "document/writeoffForm";
    }

    @PostMapping("/writeoff/save")
    public String saveWriteoff(@ModelAttribute("form") WriteoffDto writeoffDto) {
        var file = writeoffService.save(writeoffDto);
        return "redirect:/download/" + file.getName();
    }


}
