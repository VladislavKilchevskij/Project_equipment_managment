package com.example.stuffmanagerapp.util.excel.export.implementation;

import com.example.stuffmanagerapp.dto.RequestDto;
import com.example.stuffmanagerapp.util.excel.ExcelUtils;
import com.example.stuffmanagerapp.util.excel.export.Exporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.CellUtil;

import java.io.File;
import java.io.IOException;

public class RequestExporter implements Exporter<RequestDto> {
    public static final String BLANK_FILENAME = "Zajavka_na_remont";
    private static final CellReference ACT_NUMBER = new CellReference("A2");
    private static final CellReference ACT_DATE = new CellReference("C2");
    private static final int INSERTION_START_ROW = 7;

    @Override
    public File export(Integer docNum, RequestDto request) throws IOException {
        File file = ExcelUtils.getPreparedFile(BLANK_FILENAME, request.getRegDate().toString(), docNum);

        var doc = ExcelUtils.getWorkbook(file);
        Sheet sheet = doc.getSheetAt(0);

        // Заполнение ячеек до таблицы
        sheet.getRow(ACT_NUMBER.getRow()).getCell(ACT_NUMBER.getCol()).setCellValue(docNum);
        sheet.getRow(ACT_DATE.getRow()).getCell(ACT_DATE.getCol()).setCellValue(request.getRegDate());

        //Создание и заполнение ячеек таблицы.
        sheet.shiftRows(INSERTION_START_ROW, sheet.getLastRowNum(), request.getHistoryList().size());
        var amount = request.getHistoryList().size();
        for (int i = 0; i < request.getHistoryList().size(); i++) {
            Row row = sheet.getRow(INSERTION_START_ROW + i);
            for (int j = 0; j < 12; j++) {
                Cell cell = row.createCell(j);
                CellStyle style = ExcelUtils.getTableStyle(doc);
                DataFormat dataFormat = sheet.getWorkbook().createDataFormat();

                cell.setCellStyle(style);
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 6));
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 7, 11));

            row.getCell(0).setCellValue((i+1) + ".");
            row.getCell(1).setCellValue(request.getHistoryList().get(i).getEquipment().getId());
            row.getCell(2).setCellValue(request.getHistoryList().get(i).getEquipment().getEquipmentName());
            Cell malfunctionCell = row.getCell(7);
            malfunctionCell.setCellValue(request.getHistoryList().get(i).getMalfunction());
            CellUtil.setCellStyleProperty(malfunctionCell, CellUtil.WRAP_TEXT, true);
        }

        // Заполнение ячеек после таблицы.
            // Строка работник.
        Cell employeeCell = sheet.getRow(INSERTION_START_ROW + request.getHistoryList().size() + 2).getCell(10);
        employeeCell.setCellValue(String.format("%s.%s. %s",
                request.getEmployee().getFirstName().charAt(0),
                request.getEmployee().getPatronymic().charAt(0),
                request.getEmployee().getSurname()));

        // Сохранение редактированного файла, закрытие потоков ввода/вывода.
        ExcelUtils.saveWorkbook(file, doc);
        return file;
    }
}
