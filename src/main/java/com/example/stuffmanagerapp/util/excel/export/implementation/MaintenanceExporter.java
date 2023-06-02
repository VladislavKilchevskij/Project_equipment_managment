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

public class MaintenanceExporter implements Exporter<RequestDto> {
    public static final String BLANK_FILENAME = "Act_remonta";
    private static final CellReference ACT_NUMBER = new CellReference("A19");
    private static final CellReference ACT_DATE = new CellReference("C19");
    private static final CellReference REQUEST_NUMBER = new CellReference("L21");
    private static final CellReference SUM_COAST = new CellReference("L26");

    // Прочие константы
    private static final int INSERTION_START_ROW = 25;
    @Override
    public File export(Integer docNum, RequestDto request) throws IOException {

        File file = ExcelUtils.getPreparedFile(BLANK_FILENAME, request.getMaintenance().getServiceDate().toString(), docNum);

        var doc = ExcelUtils.getWorkbook(file);
        Sheet sheet = doc.getSheetAt(0);

        // Заполнение ячеек до таблицы
        sheet.getRow(ACT_NUMBER.getRow()).getCell(ACT_NUMBER.getCol()).setCellValue(docNum);
        sheet.getRow(ACT_DATE.getRow()).getCell(ACT_DATE.getCol()).setCellValue(request.getMaintenance().getServiceDate());
        sheet.getRow(REQUEST_NUMBER.getRow()).getCell(REQUEST_NUMBER.getCol()).setCellValue(request.getId());

        //Создание и заполнение ячеек таблицы.
        sheet.shiftRows(INSERTION_START_ROW, sheet.getLastRowNum(), request.getHistoryList().size());
        for (int i = 0; i < request.getHistoryList().size(); i++) {
            Row row = sheet.getRow(INSERTION_START_ROW + i);
            for (int j = 0; j < 12; j++) {
                Cell cell = row.createCell(j);
                CellStyle style = ExcelUtils.getTableStyle(doc);
                DataFormat dataFormat = sheet.getWorkbook().createDataFormat();
                if (j == 11) {
                    style.setDataFormat(dataFormat.getFormat("0.00"));
                }
                cell.setCellStyle(style);
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 5));
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 6, 10));

            row.getCell(0).setCellValue((i+1) + ".");
            row.getCell(1).setCellValue(request.getHistoryList().get(i).getEquipment().getId());
            row.getCell(2).setCellValue(request.getHistoryList().get(i).getEquipment().getEquipmentName());
            Cell serviceCell = row.getCell(6);
            serviceCell.setCellValue(request.getHistoryList().get(i).getRenderedService());
            CellUtil.setCellStyleProperty(serviceCell, CellUtil.WRAP_TEXT, true);
            row.getCell(11).setCellValue(request.getHistoryList().get(i).getServiceCoast().doubleValue());
        }

        // Заполнение ячеек после таблицы.
            // Строка "Итого"
        Cell sumCoast = sheet.getRow(SUM_COAST.getRow() + request.getHistoryList().size()).getCell(SUM_COAST.getCol());
        sumCoast.setCellFormula("SUM(L26:L" + (SUM_COAST.getRow() + request.getHistoryList().size()) + ")");

            // Строка работник.
        Cell employeeCell = sheet.getRow(sumCoast.getRow().getRowNum() + 2).getCell(10);
        employeeCell.setCellValue(String.format("%s.%s. %s",
                request.getMaintenance().getEmployee().getFirstName().charAt(0),
                request.getMaintenance().getEmployee().getPatronymic().charAt(0),
                request.getMaintenance().getEmployee().getSurname()));

        // Сохранение редактированного файла, закрытие потоков ввода/вывода.
        ExcelUtils.saveWorkbook(file, doc);
        return file;
    }
}
