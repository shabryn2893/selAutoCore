package io.github.shabryn2893.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for reading from and writing to Excel files.
 */
public class ExcelUtils {

    private static final Logger logger = LoggerUtils.getLogger(ExcelUtils.class);
    private static final String SHEET_NOT_FOUND_MESSAGE = "Sheet {} not found in file {}";
    private static final String COULD_NOT_LOAD_WORKBOOK_MESSAGE = "Could not load workbook from {}";

    // Private constructor to prevent instantiation
    private ExcelUtils() {
        // No exceptions are thrown; class should not be instantiated
    }

    /**
     * Loads a workbook from the specified file path.
     *
     * @param filePath the path to the Excel file.
     * @return the loaded Workbook, or null if an error occurs.
     */
    public static Workbook loadWorkbook(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            return createWorkbook(fis, filePath);
        } catch (IOException e) {
            logger.error("Error loading Excel file: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a sheet from the workbook by name.
     *
     * @param workbook  the Workbook to retrieve the sheet from.
     * @param sheetName the name of the sheet.
     * @return the Sheet, or null if the sheet does not exist or an error occurs.
     */
    public static Sheet getSheet(Workbook workbook, String sheetName) {
        if (workbook == null) {
            return null;
        }
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            logger.warn("Sheet {} does not exist.", sheetName);
        }
        return sheet;
    }

    /**
     * Creates a workbook from a file input stream based on the file extension.
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
                logger.error("The specified file is not a valid Excel file.");
            }
        } catch (IOException e) {
            logger.error("Error creating workbook: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the value from a specified cell.
     *
     * @param sheet    the sheet to read from.
     * @param rowIndex the index of the row (0-based).
     * @param colIndex the index of the column (0-based).
     * @return the value in the specified cell, or null if an error occurs.
     */
    public static String getCellValue(Sheet sheet, int rowIndex, int colIndex) {
        if (sheet == null) {
            return null;
        }
        try {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return null;
            }
            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                return null;
            }
            return getCellValueAsString(cell);
        } catch (Exception e) {
            logger.error("Error getting cell value: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Converts the cell value to a string based on its type.
     *
     * @param cell the cell to get the value from.
     * @return the string representation of the cell value.
     */
    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * Sets a value in the specified cell.
     *
     * @param sheet    the sheet to write to.
     * @param rowIndex the index of the row (0-based).
     * @param colIndex the index of the column (0-based).
     * @param value    the value to write.
     */
    public static void setCellValue(Sheet sheet, int rowIndex, int colIndex, String value) {
        if (sheet == null) {
            return;
        }
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
     * Retrieves all data from the sheet as a Map of row index to another Map of column index to value.
     *
     * @param sheet the sheet to read from.
     * @return a Map representing the sheet data, or an empty map if an error occurs.
     */
    public static Map<Integer, Map<Integer, String>> getAllData(Sheet sheet) {
        Map<Integer, Map<Integer, String>> data = new HashMap<>();
        if (sheet == null) {
            return data;
        }
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
     * Saves the workbook to the specified file path.
     *
     * @param workbook the workbook to save.
     * @param filePath the path to save the modified Excel file.
     */
    public static void saveWorkbook(Workbook workbook, String filePath) {
        if (workbook == null) {
            logger.error("Workbook is null. Cannot save.");
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            workbook.write(fos);
        } catch (IOException e) {
            logger.error("Error saving Excel file: {}", e.getMessage());
        } finally {
            closeWorkbook(workbook);
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
     * Loads data from the specified Excel sheet.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to load.
     * @return a Map of row index to another Map of column index to cell value.
     */
    public static Map<Integer, Map<Integer, String>> loadSheetData(String filePath, String sheetName) {
        Workbook workbook = loadWorkbook(filePath);
        if (workbook == null) {
            logger.error(COULD_NOT_LOAD_WORKBOOK_MESSAGE, filePath);
            return Collections.emptyMap();
        }
        Sheet sheet = getSheet(workbook, sheetName);
        if (sheet == null) {
            logger.error(SHEET_NOT_FOUND_MESSAGE, sheetName, filePath);
            return Collections.emptyMap();
        }
        return getAllData(sheet);
    }
    
    /**
     * Updates a cell value in the specified Excel sheet and saves the changes.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to update.
     * @param rowIndex  the index of the row (0-based).
     * @param colIndex  the index of the column (0-based).
     * @param newValue  the new value to set in the cell.
     */
    public static void updateCellValue(String filePath, String sheetName, int rowIndex, int colIndex, String newValue) {
        Workbook workbook = loadWorkbook(filePath);
        if (workbook == null) {
            logger.error(COULD_NOT_LOAD_WORKBOOK_MESSAGE, filePath);
            return;
        }
        Sheet sheet = getSheet(workbook, sheetName);
        if (sheet == null) {
            logger.error(SHEET_NOT_FOUND_MESSAGE, sheetName, filePath);
            return;
        }
        setCellValue(sheet, rowIndex, colIndex, newValue);
        saveWorkbook(workbook, filePath);
    }
    
    /**
     * Reads a specific cell value from the specified Excel sheet.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to read from.
     * @param rowIndex  the index of the row (0-based).
     * @param colIndex  the index of the column (0-based).
     * @return the value in the specified cell, or null if an error occurs.
     */
    public static String readCellValue(String filePath, String sheetName, int rowIndex, int colIndex) {
        Workbook workbook = loadWorkbook(filePath);
        if (workbook == null) {
            logger.error(COULD_NOT_LOAD_WORKBOOK_MESSAGE, filePath);
            return null;
        }
        Sheet sheet = getSheet(workbook, sheetName);
        if (sheet == null) {
            logger.error(SHEET_NOT_FOUND_MESSAGE, sheetName, filePath);
            return null;
        }
        return getCellValue(sheet, rowIndex, colIndex);
    }
    
    /**
     * Creates a new Excel file with specified sheet name and initializes the first cell.
     *
     * @param filePath  the path to save the new Excel file.
     * @param sheetName the name of the sheet to create.
     * @param value     the initial value to set in the first cell.
     */
    public static void createExcelFile(String filePath, String sheetName, String value) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        setCellValue(sheet, 0, 0, value);
        saveWorkbook(workbook, filePath);
    }
    
    /**
     * Retrieves a row of data from the specified Excel sheet based on the given test ID.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to search in.
     * @param testId    the test ID to search for in the first column.
     * @return a Map containing the header and corresponding cell values for the found test ID,
     *         or an empty Map if the test ID is not found or if an error occurs.
     */
    public static Map<String, String> getRowDataByTestId(String filePath, String sheetName, String testId) {
        Map<String, String> tcData = new HashMap<>();
        Workbook workbook = loadWorkbook(filePath);
        
        if (workbook == null) {
            logger.error(COULD_NOT_LOAD_WORKBOOK_MESSAGE, filePath);
            return Collections.emptyMap();
        }

        Sheet sheet = getSheet(workbook, sheetName);
        if (sheet == null) {
            logger.error(SHEET_NOT_FOUND_MESSAGE, sheetName, filePath);
            return Collections.emptyMap();
        }

        // Iterate through each row to find the test ID
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow != null && testId.equalsIgnoreCase(currentRow.getCell(0).getStringCellValue())) {
                // Get the header cells from the first row
                Row headerRow = sheet.getRow(0);
                for (int j = headerRow.getFirstCellNum(); j < headerRow.getLastCellNum(); j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell dataCell = currentRow.getCell(j);
                    
                    // Ensure both cells are not null before adding to the map
                    if (headerCell != null && dataCell != null) {
                        tcData.put(headerCell.getStringCellValue(), dataCell.getStringCellValue());
                    }
                }
                break; // Exit loop after finding the test ID
            }
        }
        return tcData;
    }
    
    /**
     * Retrieves a specific column value from the row corresponding to the given test ID
     * in the specified Excel sheet based on the column name.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to search in.
     * @param testId    the test ID to search for in the first column.
     * @param columnName the name of the column from which to retrieve the value.
     * @return the value from the specified column for the found test ID,
     *         or null if the test ID or column name is not found.
     */
    public static String getColumnValueByColumnNameTestId(String filePath, String sheetName, String testId, String columnName) {
        Map<String, String> rowData = getRowDataByTestId(filePath, sheetName, testId);
        return rowData != null ? rowData.get(columnName) : null;
    }


}
