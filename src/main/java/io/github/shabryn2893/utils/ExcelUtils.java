package io.github.shabryn2893.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for reading from and writing to Excel files.
 */
public class ExcelUtils {

    private static final Logger logger = LoggerUtils.getLogger(ExcelUtils.class);

    // Private constructor to prevent instantiation
    private ExcelUtils() {
        throw new UnsupportedOperationException("ExcelDataReader class should not be instantiated");
    }

    /**
     * Loads an Excel file and selects a sheet.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to load.
     * @return the loaded sheet, or null if an error occurs.
     */
    public static Sheet loadExcel(String filePath, String sheetName) {
        Workbook workbook = null;
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            workbook = createWorkbook(fis, filePath);
            if (workbook == null) {
                return null;
            }
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                logger.warn("Sheet {} does not exist.", sheetName);
            }
            return sheet;
        } catch (IOException | IllegalArgumentException e) {
            logger.error("Error loading Excel file: {}", e.getMessage());
            return null;
        } finally {
            closeWorkbook(workbook);
        }
    }

    /**
     * Creates a workbook based on the file extension.
     *
     * @param fis      the FileInputStream of the Excel file.
     * @param filePath the path to the Excel file.
     * @return the created Workbook, or null if an error occurs.
     */
    private static Workbook createWorkbook(FileInputStream fis, String filePath) {
        try {
            if (filePath.endsWith(".xlsx")) {
                return new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls")) {
                return new HSSFWorkbook(fis);
            } else {
                logger.error("The specified file is not an Excel file.");
                return null;
            }
        } catch (IOException e) {
            logger.error("Error creating workbook: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Closes the workbook, suppressing any exceptions.
     *
     * @param workbook the workbook to close.
     */
    private static void closeWorkbook(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                logger.error("Error closing workbook: {}", e.getMessage());
            }
        }
    }

    /**
     * Retrieves a value from the specified cell.
     *
     * @param sheet    the sheet to read from.
     * @param rowIndex the index of the row (0-based).
     * @param colIndex the index of the column (0-based).
     * @return the value in the specified cell, or null if an error occurs.
     */
    public static String getCellValue(Sheet sheet, int rowIndex, int colIndex) {
        try {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return null;
            }
            Cell cell = row.getCell(colIndex);
            return cell != null ? cell.toString() : null;
        } catch (Exception e) {
            logger.error("Error getting cell value: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all data from the sheet as a Map of row index to another Map of column index to value.
     *
     * @param sheet the sheet to read from.
     * @return a Map representing the sheet data, or an empty map if an error occurs.
     */
    public static Map<Integer, Map<Integer, String>> getAllData(Sheet sheet) {
        Map<Integer, Map<Integer, String>> data = new HashMap<>();
        try {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<Integer, String> rowData = new HashMap<>();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        rowData.put(j, getCellValue(sheet, i, j));
                    }
                    data.put(i, rowData);
                }
            }
        } catch (Exception e) {
            logger.error("Error getting all data: {}", e.getMessage());
        }
        return data;
    }

    /**
     * Writes a value to the specified cell.
     *
     * @param sheet    the sheet to write to.
     * @param rowIndex the index of the row (0-based).
     * @param colIndex the index of the column (0-based).
     * @param value    the value to write.
     */
    public static void setCellValue(Sheet sheet, int rowIndex, int colIndex, String value) {
        try {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                cell = row.createCell(colIndex);
            }
            cell.setCellValue(value);
        } catch (Exception e) {
            logger.error("Error setting cell value: {}", e.getMessage());
        }
    }

    /**
     * Saves the changes to the Excel file.
     *
     * @param sheet    the sheet to save.
     * @param filePath the path to save the modified Excel file.
     */
    public static void save(Sheet sheet, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            Workbook workbook = sheet.getWorkbook();
            workbook.write(fos);
        } catch (IOException e) {
            logger.error("Error saving Excel file: {}", e.getMessage());
        }
    }
}
