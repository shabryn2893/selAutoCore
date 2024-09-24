package io.github.shabryn2893.tests.utils;

import org.slf4j.Logger;
import org.testng.annotations.Test;

import io.github.shabryn2893.utils.LoggerUtils;

public class TestLoggerUtils {

	private static final Logger logger = LoggerUtils.getLogger(TestLoggerUtils.class);
	@Test
	public  void testLoggerUtils() {
		logger.info("This is an info message.");
        logger.warn("This is a warning message.");
        logger.error("This is an error message.");
	}
}
