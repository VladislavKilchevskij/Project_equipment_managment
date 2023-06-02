package com.example.stuffmanagerapp.util.excel.export.implementation;

import com.example.stuffmanagerapp.dto.writeoff.WriteoffDto;
import com.example.stuffmanagerapp.util.excel.ExcelUtils;
import com.example.stuffmanagerapp.util.excel.export.Exporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.CellUtil;

import java.io.File;
import java.io.IOException;

public class WriteoffExporter implements Exporter<WriteoffDto> {
    public static final String BLANK_FILENAME = "Act_spisania";
    private static final CellReference ACT_NUMBER = new CellReference("A19");
    private static final CellReference ACT_DATE = new CellReference("C19");
    private static final CellReference SUM_COAST = new CellReference("H26");
    private static final int INSERTION_START_ROW = 25;
    @Override
    public File export(Integer docNum, WriteoffDto writeoff) throws IOException {

        File file = ExcelUtils.getPreparedFile(BLANK_FILENAME, writeoff.getWriteoffDate().toString(), docNum);

        var doc = ExcelUtils.getWorkbook(file);
        Sheet sheet = doc.getSheetAt(0);

        // Заполнение ячеек до таблицы
        sheet.getRow(ACT_NUMBER.getRow()).getCell(ACT_NUMBER.getCol()).setCellValue(docNum);
        sheet.getRow(ACT_DATE.getRow()).getCell(ACT_DATE.getCol()).setCellValue(writeoff.getWriteoffDate());

        //Создание и заполнение ячеек таблицы.
        sheet.shiftRows(INSERTION_START_ROW, sheet.getLastRowNum(), writeoff.getEquipment().size());
        for (int i = 0; i < writeoff.getEquipment().size(); i++) {
            Row row = sheet.getRow(INSERTION_START_ROW + i);
            for (int j = 0; j < 12; j++) {
                Cell cell = row.createCell(j);
                CellStyle style = ExcelUtils.getTableStyle(doc);
                DataFormat dataFormat = sheet.getWorkbook().createDataFormat();
                if (j == 7) {
                    style.setDataFormat(dataFormat.getFormat("0.00"));
                }
                cell.setCellStyle(style);
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 6));
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 8, 11));

            row.getCell(0).setCellValue((i+1) + ".");
            row.getCell(1).setCellValue(writeoff.getEquipment().get(i).getId());
            row.getCell(2).setCellValue(writeoff.getEquipment().get(i).getEquipmentName());
            row.getCell(7).setCellValue(writeoff.getEquipment().get(i).getCoast().doubleValue());
            Cell reasonCell = row.getCell(8);
            reasonCell.setCellValue(writeoff.getEquipment().get(i).getWriteoffReason());
            CellUtil.setCellStyleProperty(reasonCell, CellUtil.WRAP_TEXT, true);
        }

        // Заполнение ячеек после таблицы.
        // Строка "Итого"
        Cell sumCoast = sheet.getRow(SUM_COAST.getRow() + writeoff.getEquipment().size()).getCell(SUM_COAST.getCol());
        sumCoast.setCellFormula("SUM(H26:H" + (SUM_COAST.getRow() + writeoff.getEquipment().size()) + ")");

        // Строка работник.
        Cell employeeCell = sheet.getRow(sumCoast.getRow().getRowNum() + 8).getCell(10);
        employeeCell.setCellValue(String.format("%s.%s. %s",
                writeoff.getEmployee().getFirstName().charAt(0),
                writeoff.getEmployee().getPatronymic().charAt(0),
                writeoff.getEmployee().getSurname()));

        // Сохранение редактированного файла, закрытие потоков ввода/вывода.
        ExcelUtils.saveWorkbook(file, doc);
        return file;
    }
}
