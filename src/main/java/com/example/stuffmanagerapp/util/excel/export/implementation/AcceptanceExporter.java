package com.example.stuffmanagerapp.util.excel.export.implementation;

import com.example.stuffmanagerapp.dto.AcceptanceDto;
import com.example.stuffmanagerapp.util.excel.ExcelUtils;
import com.example.stuffmanagerapp.util.excel.export.Exporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.io.File;

public class AcceptanceExporter implements Exporter<AcceptanceDto> {
    public static final String BLANK_FILENAME = "Act_priema";
    private static final CellReference ACT_NUMBER = new CellReference("A19");
    private static final CellReference ACT_DATE = new CellReference("C19");
    private static final CellReference SUM_AMOUNT = new CellReference("I25");
    private static final CellReference SUM_COAST = new CellReference("L25");
    public static final String INTEGER_FORMAT = "0";
    public static final String FINANCE_FORMAT = "0.00";
    private static final int INSERTION_START_ROW = 24;

    @Override
    public File export(Integer docNum, AcceptanceDto acceptance){
        File file = ExcelUtils.getPreparedFile(BLANK_FILENAME, acceptance.getAcceptanceDate().toString(), docNum);

        var doc = ExcelUtils.getWorkbook(file);
        Sheet sheet = doc.getSheetAt(0);

        // Заполнение ячеек до таблицы
        sheet.getRow(ACT_NUMBER.getRow()).getCell(ACT_NUMBER.getCol()).setCellValue(docNum);
        sheet.getRow(ACT_DATE.getRow()).getCell(ACT_DATE.getCol()).setCellValue(acceptance.getAcceptanceDate());

        //Создание и заполнение ячеек таблицы.
        sheet.shiftRows(INSERTION_START_ROW, sheet.getLastRowNum(), acceptance.getEquipmentRowAmount());
        for (int i = 0; i < acceptance.getEquipmentRowAmount(); i++) {
            Row row = sheet.getRow(INSERTION_START_ROW + i);
            for (int j = 0; j < 12; j++) {
                Cell cell = row.createCell(j);
                CellStyle style = ExcelUtils.getTableStyle(doc);
                DataFormat dataFormat = sheet.getWorkbook().createDataFormat();
                if (j == 8) {
                    style.setAlignment(HorizontalAlignment.CENTER);
                    style.setDataFormat(dataFormat.getFormat("0"));
                }
                if (j == 9 || j == 10 || j == 11) {
                    style.setDataFormat(dataFormat.getFormat("0.00"));
                    if (j == 11) {
                        String formula = "I" + (INSERTION_START_ROW + i + 1) + "*J" + (INSERTION_START_ROW + i + 1);
                        cell.setCellFormula(formula);
                    }
                }
                cell.setCellStyle(style);
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 1, 7));
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 9, 10));

            row.getCell(0).setCellValue((i+1) + ".");
            row.getCell(1).setCellValue(acceptance.getEquipment().get(i).getEquipmentName());
            row.getCell(8).setCellValue(acceptance.getEquipment().get(i).getAmount());
            row.getCell(9).setCellValue(acceptance.getEquipment().get(i).getCoast().doubleValue());
        }

        // Заполнение ячеек после таблицы.
            // Строка "Итого"
        Cell summAmount = sheet.getRow(SUM_AMOUNT.getRow() + acceptance.getEquipmentRowAmount()).getCell(SUM_AMOUNT.getCol());
        CellStyle amountStyle = summAmount.getCellStyle();
        summAmount.setCellFormula("SUM(I25:I" + (SUM_AMOUNT.getRow() + acceptance.getEquipmentRowAmount()) + ")");
        amountStyle.setAlignment(HorizontalAlignment.CENTER);
        DataFormat amountFormat = doc.createDataFormat();
        amountStyle.setDataFormat(amountFormat.getFormat(INTEGER_FORMAT));
        summAmount.setCellStyle(amountStyle);

        Cell summCoast = sheet.getRow(SUM_COAST.getRow() + acceptance.getEquipmentRowAmount()).getCell(SUM_COAST.getCol());
        CellStyle coastStyle = summCoast.getCellStyle();
        summCoast.setCellFormula("SUM(L25:L" + (SUM_COAST.getRow() + acceptance.getEquipmentRowAmount()) + ")");
        coastStyle.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat coastFormat = doc.createDataFormat();
        coastStyle.setDataFormat(coastFormat.getFormat(FINANCE_FORMAT));
        summCoast.setCellStyle(coastStyle);

            // Строки поставщик и работник.
        Cell providerCell = sheet.getRow(summAmount.getRow().getRowNum() + 2).getCell(2);
        providerCell.setCellValue(acceptance.getProvider());
        
        Cell employeeCell = sheet.getRow(providerCell.getRow().getRowNum() + 3).getCell(10);
        employeeCell.setCellValue(String.format("%s.%s. %s",
                acceptance.getEmployee().getFirstName().charAt(0),
                acceptance.getEmployee().getPatronymic().charAt(0),
                acceptance.getEmployee().getSurname()));

        // Сохранение редактированного файла, закрытие потоков ввода/вывода, открытие файла в Excel.
        ExcelUtils.saveWorkbook(file, doc);
        return file;
    }
}
