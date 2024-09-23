package io.github.shabryn2893.tests.utils;

import org.slf4j.Logger;
import org.testng.annotations.Test;

import io.github.shabryn2893.utils.DateManager;
import io.github.shabryn2893.utils.LoggerUtils;

public class TestDateManager {
	private static final Logger logger = LoggerUtils.getLogger(TestDateManager.class);
	@Test
	public  void testDateManager() {
		logger.info("Next Day Date: {}",DateManager.getDaysOut(1,"MMM-dd-yyyy"));
	}
}
