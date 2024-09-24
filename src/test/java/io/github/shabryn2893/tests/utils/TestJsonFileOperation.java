package io.github.shabryn2893.tests.utils;

import org.slf4j.Logger;
import org.testng.annotations.Test;

import io.github.shabryn2893.utils.JsonFileManager;
import io.github.shabryn2893.utils.LoggerUtils;

public class TestJsonFileOperation {
	private static final Logger logger = LoggerUtils.getLogger(TestJsonFileOperation.class);
	@Test
	public void testJsonFileManager() {
        // Example usage
        String filePath = "./src/main/resources/data.json";

        // Get value from JSON
        String value = JsonFileManager.getJsonData(filePath, "name");
        logger.info("Value: {}", value);

        // Set value in JSON
        JsonFileManager.setJsonData(filePath, "age", "18");
    }

}
