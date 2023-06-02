package com.example.stuffmanagerapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
public class DownloadController {
    private static final String DOCS_PATH = "src/main/resources/static/docs/";

    @GetMapping("/download/{fileName}")
    public void downloadDocument(HttpServletResponse response,
                                 @PathVariable("fileName") String fileName)
    {
        File file = new File(DOCS_PATH + fileName);
        if (file.exists())
        {
            response.setContentType("application/xls");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try
            {
                Files.copy(file.toPath(), response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            file.delete();
        }
    }
}
