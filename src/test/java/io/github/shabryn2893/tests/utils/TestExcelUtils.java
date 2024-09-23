package io.github.shabryn2893.tests.utils;

import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.Test;

import io.github.shabryn2893.utils.ExcelUtils;

public class TestExcelUtils {
	
	@Test
	public void testExcelUtils() {
		
		// Define the path to the Excel file
        String filePath = "./src/main/resources/data.xlsx"; // Replace with your actual file path
        String sheetName = "Sheet1"; // Replace with your actual sheet name

        // Load the Excel sheet
        Sheet sheet = ExcelUtils.loadExcel(filePath, sheetName);
        if (sheet == null) {
            System.out.println("Failed to load sheet: " + sheetName);
            return;
        }

        // Retrieve all data from the sheet
        Map<Integer, Map<Integer, String>> allData = ExcelUtils.getAllData(sheet);
        System.out.println("Excel Data:");
        allData.forEach((rowIndex, rowData) -> {
            rowData.forEach((colIndex, value) -> {
                System.out.println("Row: " + rowIndex + ", Column: " + colIndex + ", Value: " + value);
            });
        });

        // Modify a specific cell
        int targetRow = 1; // 0-based index, change to the row you want to modify
        int targetColumn = 1; // 0-based index, change to the column you want to modify
        String newValue = "Updated Value"; // The new value to set

        ExcelUtils.setCellValue(sheet, targetRow, targetColumn, newValue);
        System.out.println("Updated cell at row " + targetRow + ", column " + targetColumn);

        // Save the changes back to the Excel file
        ExcelUtils.save(sheet, filePath);
        System.out.println("Changes saved to " + filePath);
		
	}

}
