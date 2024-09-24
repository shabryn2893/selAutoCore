package io.github.shabryn2893.tests.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.testng.annotations.Test;

import io.github.shabryn2893.utils.ExcelUtils;
import io.github.shabryn2893.utils.LoggerUtils;

public class TestExcelUtils {
	private static final Logger logger = LoggerUtils.getLogger(TestExcelUtils.class);
	@Test
	public void testExcelUtils() {
		
		// Create a new Excel file
        String filePath = "./src/main/resources/data.xlsx";
        ExcelUtils.createExcelFile(filePath, "Sheet1", "Hello, World!");

        // Read a cell value
        String cellValue = ExcelUtils.readCellValue(filePath, "Sheet1", 0, 0);
        logger.info("Cell Value: {}",cellValue);

        // Update a cell value
        ExcelUtils.updateCellValue(filePath, "Sheet1", 0, 0, "Updated Value");

        // Load sheet data
        Map<Integer, Map<Integer, String>> data = ExcelUtils.loadSheetData(filePath, "Sheet1");
        logger.info("Sheet Data: {}",data);
	}

}
