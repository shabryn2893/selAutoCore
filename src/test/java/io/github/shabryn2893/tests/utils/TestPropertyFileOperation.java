package io.github.shabryn2893.tests.utils;

import org.slf4j.Logger;
import org.testng.annotations.Test;

import io.github.shabryn2893.utils.LoggerUtils;
import io.github.shabryn2893.utils.PropertyFileManager;

public class TestPropertyFileOperation {

	private static final Logger logger = LoggerUtils.getLogger(TestPropertyFileOperation.class);
	@Test
	public  void testPropertyFileManager() {

		String filePath = "./src/main/resources/config.properties";

		// Reading properties
		String value = PropertyFileManager.getProperty(filePath, "name");
		logger.info("Value: {}", value);

		// Setting properties
		PropertyFileManager.setProperty(filePath, "age", "18");

		// Removing a property
		PropertyFileManager.removeProperty(filePath, "id");

	}
}
