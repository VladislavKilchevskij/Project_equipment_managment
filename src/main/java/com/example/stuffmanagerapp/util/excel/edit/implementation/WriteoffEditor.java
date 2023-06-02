package com.example.stuffmanagerapp.util.excel.edit.implementation;

import com.example.stuffmanagerapp.util.excel.ExcelUtils;
import com.example.stuffmanagerapp.util.excel.edit.Editor;
import com.example.stuffmanagerapp.util.excel.entity.descendants.MaintenanceDocument;
import com.example.stuffmanagerapp.util.excel.entity.descendants.WriteoffDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

public class WriteoffEditor implements Editor<WriteoffDocument> {
    private static final String FILE_NAME = "Act_spisania";
    private static final CellReference POSITION = new CellReference("H2");
    private static final CellReference ORGANISATION_TOP = new CellReference("H3");
    private static final CellReference ORGANISATION_MIDDLE = new CellReference("C12");
    private static final CellReference DEPARTMENT = new CellReference("C15");
    private static final CellReference UNP = new CellReference("K12");
    private static final CellReference MANAGER = new CellReference("K5");
    private static final CellReference COMISSION_MEMBER_POSITION_1 = new CellReference("D28");
    private static final CellReference COMISSION_MEMBER_NAME_1 = new CellReference("K28");
    private static final CellReference COMISSION_MEMBER_POSITION_2 = new CellReference("D31");
    private static final CellReference COMISSION_MEMBER_NAME_2 = new CellReference("K31");

    @Override
    public void updateDocument(WriteoffDocument document) {
        var file = ExcelUtils.getBlankFile(FILE_NAME);
        var workbook = ExcelUtils.getWorkbook(file);
        var sheet = workbook.getSheetAt(0);

        sheet.getRow(POSITION.getRow()).getCell(POSITION.getCol()).setCellValue(document.getChiefPosition());
        sheet.getRow(MANAGER.getRow()).getCell(MANAGER.getCol()).setCellValue(document.getChiefName());
        sheet.getRow(ORGANISATION_TOP.getRow()).getCell(ORGANISATION_TOP.getCol()).setCellValue(document.getOrganisationName());
        sheet.getRow(ORGANISATION_MIDDLE.getRow()).getCell(ORGANISATION_MIDDLE.getCol()).setCellValue(document.getOrganisationName());
        sheet.getRow(DEPARTMENT.getRow()).getCell(DEPARTMENT.getCol()).setCellValue(document.getDepartmentName());
        sheet.getRow(UNP.getRow()).getCell(UNP.getCol()).setCellValue(document.getUnp().doubleValue());

        sheet.getRow(COMISSION_MEMBER_POSITION_1.getRow()).getCell(COMISSION_MEMBER_POSITION_1.getCol()).setCellValue(document.getComissionMemberPosition1());
        sheet.getRow(COMISSION_MEMBER_NAME_1.getRow()).getCell(COMISSION_MEMBER_NAME_1.getCol()).setCellValue(document.getComissionMemberName1());
        sheet.getRow(COMISSION_MEMBER_POSITION_2.getRow()).getCell(COMISSION_MEMBER_POSITION_2.getCol()).setCellValue(document.getComissionMemberPosition2());
        sheet.getRow(COMISSION_MEMBER_NAME_2.getRow()).getCell(COMISSION_MEMBER_NAME_2.getCol()).setCellValue(document.getComissionMemberName2());

        ExcelUtils.saveWorkbook(file, workbook);
    }

    @Override
    public WriteoffDocument getDocument() {
        var document = new WriteoffDocument();
        var file = ExcelUtils.getBlankFile(FILE_NAME);
        var workbook = ExcelUtils.getWorkbook(file);
        var sheet = workbook.getSheetAt(0);

        document.setChiefPosition(sheet.getRow(POSITION.getRow()).getCell(POSITION.getCol()).getStringCellValue());
        document.setChiefName(sheet.getRow(MANAGER.getRow()).getCell(MANAGER.getCol()).getStringCellValue());
        document.setOrganisationName(sheet.getRow(ORGANISATION_TOP.getRow()).getCell(ORGANISATION_TOP.getCol()).getStringCellValue());
        document.setDepartmentName(sheet.getRow(DEPARTMENT.getRow()).getCell(DEPARTMENT.getCol()).getStringCellValue());
        document.setUnp((int) sheet.getRow(UNP.getRow()).getCell(UNP.getCol()).getNumericCellValue());

        document.setComissionMemberPosition1(sheet.getRow(COMISSION_MEMBER_POSITION_1.getRow()).getCell(COMISSION_MEMBER_POSITION_1.getCol()).getStringCellValue());
        document.setComissionMemberName1(sheet.getRow(COMISSION_MEMBER_NAME_1.getRow()).getCell(COMISSION_MEMBER_NAME_1.getCol()).getStringCellValue());
        document.setComissionMemberPosition2(sheet.getRow(COMISSION_MEMBER_POSITION_2.getRow()).getCell(COMISSION_MEMBER_POSITION_2.getCol()).getStringCellValue());
        document.setComissionMemberName2(sheet.getRow(COMISSION_MEMBER_NAME_2.getRow()).getCell(COMISSION_MEMBER_NAME_2.getCol()).getStringCellValue());

        return document;
    }
}
