package com.example.stuffmanagerapp.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class ExcelUtils {
    public static final String PATH = "src/main/resources/static/docs/";
    private static final String NAME_SEPARATOR = "_";
    public static final String EXCEL_EXTENSION = ".xls";

    private static final String FONT_NAME = "Times New Roman";
    private static final short FONT_SIZE = 14;

    public static File getBlankFile(String filename) {
        return new File(PATH + filename + EXCEL_EXTENSION);
    }
    public static File getPreparedFile(String blankFileName, String docDate, Integer docNum) {

        var source = getBlankFile(blankFileName);
        var destination = new File(PATH + blankFileName + NAME_SEPARATOR
                + docDate + NAME_SEPARATOR
                + docNum + EXCEL_EXTENSION);

        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destination;
    }
    public static CellStyle getTableStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints(FONT_SIZE);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        return style;
    }

    public static Workbook getWorkbook(File file) {
        Workbook doc = null;
        try (var fis = new FileInputStream(file)){
            doc = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    public static void saveWorkbook(File file, Workbook workbook) {
        try (FileOutputStream os = new FileOutputStream(file)) {
            workbook.write(os);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
