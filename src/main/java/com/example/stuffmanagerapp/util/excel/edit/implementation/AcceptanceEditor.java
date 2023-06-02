package com.example.stuffmanagerapp.util.excel.edit.implementation;

import com.example.stuffmanagerapp.util.excel.ExcelUtils;
import com.example.stuffmanagerapp.util.excel.edit.Editor;
import com.example.stuffmanagerapp.util.excel.entity.descendants.AcceptanceDocument;
import org.apache.poi.ss.util.CellReference;

public class AcceptanceEditor implements Editor<AcceptanceDocument> {
    private static final String FILE_NAME = "Act_priema";
    private static final CellReference POSITION = new CellReference("H2");
    private static final CellReference ORGANISATION_TOP = new CellReference("H3");
    private static final CellReference ORGANISATION_MIDDLE = new CellReference("C12");
    private static final CellReference DEPARTMENT = new CellReference("C15");
    private static final CellReference UNP = new CellReference("K12");
    private static final CellReference MANAGER = new CellReference("K5");


    @Override
    public void updateDocument(AcceptanceDocument document) {
        var file = ExcelUtils.getBlankFile(FILE_NAME);
        var workbook = ExcelUtils.getWorkbook(file);
        var sheet = workbook.getSheetAt(0);

        sheet.getRow(POSITION.getRow()).getCell(POSITION.getCol()).setCellValue(document.getChiefPosition());
        sheet.getRow(MANAGER.getRow()).getCell(MANAGER.getCol()).setCellValue(document.getChiefName());
        sheet.getRow(ORGANISATION_TOP.getRow()).getCell(ORGANISATION_TOP.getCol()).setCellValue(document.getOrganisationName());
        sheet.getRow(ORGANISATION_MIDDLE.getRow()).getCell(ORGANISATION_MIDDLE.getCol()).setCellValue(document.getOrganisationName());
        sheet.getRow(DEPARTMENT.getRow()).getCell(DEPARTMENT.getCol()).setCellValue(document.getDepartmentName());
        sheet.getRow(UNP.getRow()).getCell(UNP.getCol()).setCellValue(document.getUnp().doubleValue());

        ExcelUtils.saveWorkbook(file, workbook);
    }

    @Override
    public AcceptanceDocument getDocument() {
        var document = new AcceptanceDocument();
        var file = ExcelUtils.getBlankFile(FILE_NAME);
        var workbook = ExcelUtils.getWorkbook(file);
        var sheet = workbook.getSheetAt(0);

        document.setChiefPosition(sheet.getRow(POSITION.getRow()).getCell(POSITION.getCol()).getStringCellValue());
        document.setChiefName(sheet.getRow(MANAGER.getRow()).getCell(MANAGER.getCol()).getStringCellValue());
        document.setOrganisationName(sheet.getRow(ORGANISATION_TOP.getRow()).getCell(ORGANISATION_TOP.getCol()).getStringCellValue());
        document.setDepartmentName(sheet.getRow(DEPARTMENT.getRow()).getCell(DEPARTMENT.getCol()).getStringCellValue());
        document.setUnp((int) sheet.getRow(UNP.getRow()).getCell(UNP.getCol()).getNumericCellValue());
        return document;
    }
}
